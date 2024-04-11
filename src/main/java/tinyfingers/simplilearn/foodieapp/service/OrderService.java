package tinyfingers.simplilearn.foodieapp.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.exception.ForbiddenOperationException;
import tinyfingers.simplilearn.foodieapp.exception.InvalidOrder;
import tinyfingers.simplilearn.foodieapp.exception.ResourceNotFoundException;
import tinyfingers.simplilearn.foodieapp.mapper.DomainApiMapper;
import tinyfingers.simplilearn.foodieapp.mapper.DomainMapper;
import tinyfingers.simplilearn.foodieapp.model.api.OrderAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Order;
import tinyfingers.simplilearn.foodieapp.model.domain.OrderStatus;
import tinyfingers.simplilearn.foodieapp.repository.CartRepository;
import tinyfingers.simplilearn.foodieapp.repository.OrderRepository;
import tinyfingers.simplilearn.foodieapp.repository.RestaurantsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
  private final DomainApiMapper domainApiMapper;
  private final DomainMapper domainMapper;

  private final CartRepository cartRepository;
  private final RestaurantsRepository restaurantsRepository;
  private final OrderRepository orderRepository;

  private final EntityManager entityManager;

  public Order createOrder(Long cartId, String userId) {
    val order = new Order();
    order.setUserId(userId);

    val cart = cartRepository
            .findById(cartId)
            .orElseThrow(() -> new InvalidOrder("Cart not found"));
    val restaurant = restaurantsRepository.findById(cart.getRestaurant()
            .getRestaurantId()).orElseThrow(() -> new InvalidOrder("Restaurant not found"));
    order.setRestaurant(restaurant);

    val orderItems = cart.getCartItems()
            .stream()
            .map(domainMapper::map)
            .map(entityManager::merge)
            .toList();

    order.setOrderItems(orderItems);
    order.setOrderDate(LocalDateTime.now());

    return orderRepository.save(order);
  }

  public void cancelOrder(String userId, Long orderId) {
    val order = orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    if (!order.getUserId().equals(userId)) throw new ForbiddenOperationException("User not allowed to cancel order");
    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
  }

  public OrderAPI updateOrderStatus(Long orderId, OrderStatus orderStatus) {
    val order = orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    order.setOrderStatus(orderStatus);
    return domainApiMapper.map(orderRepository.save(order));
  }

  public List<OrderAPI> getOrders(String userId) {
    return orderRepository.findAllByUserId(userId)
            .stream()
            .map(domainApiMapper::map)
            .collect(Collectors.toList());
  }

  public OrderAPI getOrder(Long orderId) {
    return orderRepository.findById(orderId)
            .map(order -> {
              OrderAPI orderAPI = domainApiMapper.map(order);
              orderAPI.setEstimatedDeliveryTime(orderAPI.getOrderDate().plusMinutes(30));
              return orderAPI;
            })
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
  }
}
