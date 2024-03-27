package tinyfingers.simplilearn.foodieapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.springframework.stereotype.Component;
import tinyfingers.simplilearn.foodieapp.model.Order;
import tinyfingers.simplilearn.foodieapp.model.OrderItem;
import tinyfingers.simplilearn.foodieapp.model.api.OrderDto;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {

  OrderDto map(Order order);

  @Mapping(target = "orderStatus", source = "orderStatus", defaultValue = "INITIATED")
  Order map(OrderDto orderDto);

  default OrderDto map(Order order, String restaurantName) {
    OrderDto orderDto = map(order);
    orderDto.setRestaurantName(restaurantName);
    return orderDto;
  }

  default Order map(OrderDto orderDto, String userId) {
    Order order = map(orderDto);
    order.setUserId(userId);
    return order;
  }
}
