package tinyfingers.simplilearn.foodieapp.service.externalapi;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Restaurant;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Sellable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantApiService {
  public String getRestaurantNameById(Long id) {
    return "Demo Restaurant";
  }

  public Restaurant getRestaurantById(Long id) {
    if (id != 1) return null;
    return new Restaurant("Nonna", 1, List.of(
            new Sellable("Caesar", "Salad with chicken stripes", "APPETIZER", 1L),
            new Sellable("Coca-Cola", "", "DRINKS", 10L)
    ), "123 rue de Grande");
  }

  public List<Restaurant> getRestaurants() {
    val restaurant1 = new Restaurant("Nonna", 1, List.of(
            new Sellable("Caesar", "Salad with chicken stripes", "APPETIZER", 1L),
            new Sellable("Coca-Cola", "", "DRINKS", 10L)
    ), "123 rue de Grande");
    val restaurant2 = new Restaurant("Salam", 2, List.of(
            new Sellable("Kebab", "Kebad meat with Turkish bread", "MAIN", 1L),
            new Sellable("Coca-Cola", "", "DRINKS", 10L)
    ), "13 rue de Grande");

    return List.of(restaurant1, restaurant2);
  }
}
