package tinyfingers.simplilearn.foodieapp.service.externalapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.repository.RestaurantsRepository;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Restaurant;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantApiService {
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
}
