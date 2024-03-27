package tinyfingers.simplilearn.foodieapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tinyfingers.simplilearn.foodieapp.mapper.RequestResponseMapper;
import tinyfingers.simplilearn.foodieapp.model.api.CreateOrderRequest;
import tinyfingers.simplilearn.foodieapp.model.api.CreateOrderResponse;
import tinyfingers.simplilearn.foodieapp.model.api.OrderDto;
import tinyfingers.simplilearn.foodieapp.model.api.OrderStatus;
import tinyfingers.simplilearn.foodieapp.service.OrderService;
import tinyfingers.simplilearn.foodieapp.service.externalapi.RestaurantApiService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class OrdersController {
  private final OrderService orderService;
  private final RestaurantApiService restaurantApiService;
  private final RequestResponseMapper mapper;

  @GetMapping("/orders")
  public ResponseEntity<List<OrderDto>> getAllOrders() {
    return ResponseEntity.ok(orderService.getOrderDtos());
  }

  @PostMapping("/orders")
  public ResponseEntity<CreateOrderResponse> createOrder(@RequestParam String userId, @RequestBody CreateOrderRequest request) {
    log.info("Creating order for user {} with order {}", userId, request);

    OrderDto orderDto = mapper.map(request);

    return ResponseEntity.ok(new CreateOrderResponse(orderService.createOrder(userId, orderDto)));
  }

  @PutMapping("/orders/{id}/updateStatus")
  public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
    log.info("Updating order status for user {} with order {}", id, status);
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status);
      return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/orders/{id}/cancel")
  public ResponseEntity cancelOrder(@RequestParam String userId, @PathVariable Long id) {
    log.info("Cancelling order {}", id);

    try {
      orderService.cancelOrder(userId, id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
