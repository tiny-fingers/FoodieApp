package tinyfingers.simplilearn.foodieapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.mapper.OrderMapper;
import tinyfingers.simplilearn.foodieapp.model.Order;
import tinyfingers.simplilearn.foodieapp.model.api.OrderDto;
import tinyfingers.simplilearn.foodieapp.model.api.OrderStatus;
import tinyfingers.simplilearn.foodieapp.repository.OrderRepository;
import tinyfingers.simplilearn.foodieapp.service.externalapi.RestaurantApiService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final RestaurantApiService restaurantApiService;
  private final OrderMapper mapper;

  public Long createOrder(String userId, OrderDto orderDto) {
    val order = mapper.map(orderDto, userId);
    log.info("Order created: {}", order);
    return orderRepository.save(order).getId();
  }

  public void cancelOrder(String userId, Long orderId) {
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
  }

  public OrderDto updateOrderStatus(Long orderId, OrderStatus orderStatus) {
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    order.setOrderStatus(orderStatus);
    return mapper.map(orderRepository.save(order));
  }

  public List<OrderDto> getOrderDtos() {
    return orderRepository.findAll()
            .stream()
            .map(order -> mapper.map(order, getRestaurantName(order)))
            .collect(Collectors.toList());
  }

  private String getRestaurantName(Order order) {
    return restaurantApiService.getRestaurantNameById(order.getRestaurantId());
  }
}
