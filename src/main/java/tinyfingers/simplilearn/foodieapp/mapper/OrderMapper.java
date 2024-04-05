package tinyfingers.simplilearn.foodieapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import tinyfingers.simplilearn.foodieapp.model.api.OrderAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Order;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {

  OrderAPI map(Order order);

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
