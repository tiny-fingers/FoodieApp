package tinyfingers.simplilearn.foodieapp.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import tinyfingers.simplilearn.foodieapp.model.domain.CartItem;
import tinyfingers.simplilearn.foodieapp.model.domain.OrderItem;

@Mapper(componentModel = "spring")
@Component
public interface DomainMapper {

  OrderItem map(CartItem cartItem);
}
