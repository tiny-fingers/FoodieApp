package tinyfingers.simplilearn.foodieapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantDetails;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantMenu;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Restaurant;

@Mapper(componentModel = "spring")
@Component
public interface RestaurantMapper {
  @Mapping(source = "sellable", target = "menu")
  RestaurantMenu map(Restaurant restaurant);

  @Mapping(source = "restaurantName", target = "name")
  RestaurantDetails toDetails(Restaurant restaurant);
}
