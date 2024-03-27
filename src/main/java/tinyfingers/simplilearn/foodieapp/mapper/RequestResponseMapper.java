package tinyfingers.simplilearn.foodieapp.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import tinyfingers.simplilearn.foodieapp.model.api.CreateOrderRequest;
import tinyfingers.simplilearn.foodieapp.model.api.OrderDto;

@Mapper(componentModel = "spring")
@Component
public interface RequestResponseMapper {
  OrderDto map(CreateOrderRequest createOrderRequest);

}
