package tinyfingers.simplilearn.foodieapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.model.domain.Restaurant;
import tinyfingers.simplilearn.foodieapp.repository.RestaurantsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
  private final RestaurantsRepository restaurantsRepository;

  public String getRestaurantNameById(Long id) {
    return "Demo Restaurant";
  }

  public List<Restaurant> getRestaurants() {
    return restaurantsRepository.findAll();
  }

  public List<Restaurant> findbyKeyword(List<String> keyword) {
    return restaurantsRepository.findAllWithKeyword(keyword);
  }

  public Restaurant getRestaurantById(Long id) {
    return restaurantsRepository.findById(id).orElse(null);
  }

  public boolean isSellableFromRestaurant(List<Long> sellableIds, Long restaurantId) {
    return restaurantsRepository.doSellableBelongToRestaurant(sellableIds, restaurantId);
  }


}
