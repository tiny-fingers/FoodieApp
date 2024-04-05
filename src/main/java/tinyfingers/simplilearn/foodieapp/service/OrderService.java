package tinyfingers.simplilearn.foodieapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.exception.ResourceNotFoundException;
import tinyfingers.simplilearn.foodieapp.mapper.OrderMapper;
import tinyfingers.simplilearn.foodieapp.model.api.OrderAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Order;
import tinyfingers.simplilearn.foodieapp.model.domain.OrderStatus;
import tinyfingers.simplilearn.foodieapp.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final RestaurantService restaurantApiService;
  private final OrderMapper mapper;

  public Long createOrder(String userId, OrderAPI orderDto) {
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

  public OrderAPI updateOrderStatus(Long orderId, OrderStatus orderStatus) {
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    order.setOrderStatus(orderStatus);
    return mapper.map(orderRepository.save(order));
  }

  public List<OrderAPI> getOrderDtos() {
    return orderRepository.findAll()
            .stream()
            .map(order -> mapper.map(order, getRestaurantName(order)))
            .collect(Collectors.toList());
  }

  public boolean isOrderValid(Long restaurantId, List<Long> sellableIds) {
    return restaurantApiService.isSellableFromRestaurant(sellableIds, restaurantId);
  }

  public OrderAPI getOrderDto(Long orderId) {

    return orderRepository.findById(orderId)
            .map(mapper::map)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
  }

  private String getRestaurantName(Order order) {
    return restaurantApiService.getRestaurantNameById(order.getRestaurantId());
  }
}
