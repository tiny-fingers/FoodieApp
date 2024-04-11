package tinyfingers.simplilearn.foodieapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import tinyfingers.simplilearn.foodieapp.model.api.CartAPI;
import tinyfingers.simplilearn.foodieapp.model.api.CartItemAPI;
import tinyfingers.simplilearn.foodieapp.model.api.OrderAPI;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Cart;
import tinyfingers.simplilearn.foodieapp.model.domain.CartItem;
import tinyfingers.simplilearn.foodieapp.model.domain.Order;
import tinyfingers.simplilearn.foodieapp.model.domain.Restaurant;

@Mapper(componentModel = "spring")
@Component
public interface DomainApiMapper {
  @Mapping(target = "restaurantName", source = "restaurant.restaurantName")
  @Mapping(target = "restaurantId", source = "restaurant.restaurantId")
  OrderAPI map(Order order);
  @Mapping(target = "restaurantId", source = "restaurant.restaurantId")
  @Mapping(target = "restaurantName", source = "restaurant.restaurantName")
  CartAPI map(Cart cart);
  @Mapping(target = "restaurant", ignore = true)
  Cart map(CartAPI cart);
  @Mapping(target = "unitPrice", source = "menuItem.price")
  @Mapping(target = "menuItemId", source = "menuItem.id")
  CartItemAPI map(CartItem cartItem);
  CartItem map(CartItemAPI cartItem);
  RestaurantAPI map(Restaurant restaurant);
  Restaurant map(RestaurantAPI restaurant);
  @Mapping(target = "orderStatus", source = "orderStatus", defaultValue = "INITIATED")
  Order map(OrderAPI orderDto);
  default OrderAPI map(Order order, String restaurantName) {
    OrderAPI orderDto = map(order);
    orderDto.setRestaurantName(restaurantName);
    return orderDto;
  }
  default Order map(OrderAPI orderDto, String userId) {
    Order order = map(orderDto);
    order.setUserId(userId);
    return order;
  }
}
