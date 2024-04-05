package tinyfingers.simplilearn.foodieapp.controller;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tinyfingers.simplilearn.foodieapp.exception.ResourceNotFoundException;
import tinyfingers.simplilearn.foodieapp.mapper.DomainMapper;
import tinyfingers.simplilearn.foodieapp.model.api.CreateOrderRequest;
import tinyfingers.simplilearn.foodieapp.model.api.CreateOrderResponse;
import tinyfingers.simplilearn.foodieapp.model.api.OrderAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Order;
import tinyfingers.simplilearn.foodieapp.model.domain.OrderStatus;
import tinyfingers.simplilearn.foodieapp.repository.CartRepository;
import tinyfingers.simplilearn.foodieapp.repository.OrderRepository;
import tinyfingers.simplilearn.foodieapp.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:4200")
public class OrdersController {
  private final OrderService orderService;
  private final DomainMapper domainMapper;

  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;

  private final EntityManager entityManager;

  @GetMapping("/orders")
  public ResponseEntity<List<OrderAPI>> getAllOrders() {
    return ResponseEntity.ok(orderService.getOrderDtos());
  }

  @PostMapping("/orders")
  @Transactional
  public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
    log.info("Creating order for userId {} and cart id {}", request.getUserId(), request.getCartId());

    val order = new Order();
    order.setUserId(request.getUserId());

    val cart = cartRepository.findById(request.getCartId())
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    order.setRestaurantId(cart.getRestaurant().getRestaurantId());

    val orderItems = cart.getCartItems()
            .stream()
            .map(domainMapper::map)
            .map(entityManager::merge)
            .toList();

    order.setOrderItems(orderItems);
    order.setOrderDate(LocalDateTime.now());

    val orderId = orderRepository.save(order).getId();

    val estimatedDeliveryTime = order.getOrderDate().plusMinutes(30);
    val response = new CreateOrderResponse(orderId, order.getOrderStatus().name(), order.getOrderDate(), estimatedDeliveryTime);

    cartRepository.deleteById(request.getCartId());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/orders/{orderId}")
  public ResponseEntity<OrderAPI> getOrder(@PathVariable Long orderId) {
    log.info("Retrieving order with id {}", orderId);

    return ResponseEntity.ok(orderService.getOrderDto(orderId));
  }

  @PutMapping("/orders/{id}/updateStatus")
  public ResponseEntity<OrderAPI> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
    log.info("Updating order status for user {} with order {}", id, status);
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status);
      return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/orders/{id}/cancel")
  @ResponseStatus(HttpStatus.OK)
  public void cancelOrder(@RequestParam String userId, @PathVariable Long id) {
    log.info("Cancelling order {}", id);
    try {
      orderService.cancelOrder(userId, id);
    } catch (Exception e) {
      throw new ResourceNotFoundException("Order not found");
    }
  }
}
