package tinyfingers.simplilearn.foodieapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tinyfingers.simplilearn.foodieapp.model.api.CreateOrderRequest;
import tinyfingers.simplilearn.foodieapp.model.api.CreateOrderResponse;
import tinyfingers.simplilearn.foodieapp.model.api.OrderAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.OrderStatus;
import tinyfingers.simplilearn.foodieapp.service.CartService;
import tinyfingers.simplilearn.foodieapp.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@RestController
public class OrdersController {
  private final OrderService orderService;
  private final CartService cartService;

  @GetMapping("/orders")
  public ResponseEntity<List<OrderAPI>> getAllOrders() {
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("getAllOrders for user {}", principal.getUsername());

    return ResponseEntity.ok(orderService.getOrders(principal.getUsername()));
  }

  @PostMapping("/orders")
  @Transactional
  public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("Creating order for userId {} and cart id {}", principal.getUsername(), request.getCartId());

    val order = orderService.createOrder(request.getCartId(), principal.getUsername());

    val estimatedDeliveryTime = order.getOrderDate().plusMinutes(30);
    val response = new CreateOrderResponse(order.getId(), order.getOrderStatus().name(), order.getOrderDate(), estimatedDeliveryTime);

    cartService.deleteCart(request.getCartId());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/orders/{orderId}")
  public ResponseEntity<OrderAPI> getOrder(@PathVariable Long orderId) {
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("Retrieving order with id {} for username {}", orderId, principal.getUsername());

    return ResponseEntity.ok(orderService.getOrder(orderId));
  }

  @PutMapping("/orders/{id}/updateStatus")
  public ResponseEntity<OrderAPI> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
    log.info("Updating order status for user {} with order {}", id, status);

    OrderStatus orderStatus = OrderStatus.valueOf(status);
    return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
  }

  @PostMapping("/orders/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void cancelOrder(@PathVariable Long id, @RequestParam("action") String action) {
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("Cancelling order with id {} for username {}", id, principal.getUsername());
    if (action.equalsIgnoreCase("CANCEL")) orderService.cancelOrder(principal.getUsername(), id);
    else throw new IllegalArgumentException();
  }
}
