package tinyfingers.simplilearn.foodieapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantAPI;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantMenuAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Restaurant;

@Mapper(componentModel = "spring")
@Component
public interface RestaurantMapper {
  @Mapping(source = "sellable", target = "menu")
  RestaurantMenuAPI map(Restaurant restaurant);

  @Mapping(source = "restaurantName", target = "name")
  RestaurantAPI.RestaurantDetails toDetails(Restaurant restaurant);

  default RestaurantAPI toRestaurantDto(Restaurant restaurant) {
    RestaurantAPI restaurantDto = new RestaurantAPI();
    restaurantDto.setRestaurantId(restaurant.getRestaurantId());

    restaurantDto.setMenu(restaurant.getSellable());

    RestaurantAPI.RestaurantDetails restaurantDetails = toDetails(restaurant);
    restaurantDto.setRestaurantDetails(restaurantDetails);

    return restaurantDto;
  }
}
