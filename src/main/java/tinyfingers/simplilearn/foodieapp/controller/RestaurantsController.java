package tinyfingers.simplilearn.foodieapp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tinyfingers.simplilearn.foodieapp.mapper.RestaurantMapper;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantDetails;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantMenu;
import tinyfingers.simplilearn.foodieapp.service.externalapi.RestaurantApiService;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Restaurant;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class RestaurantsController {
  private final RestaurantApiService restaurantApiService;
  private final RestaurantMapper mapper;

  @GetMapping("/restaurants")
  public ResponseEntity<List<Restaurant>> getRestaurants() {
    return ResponseEntity.ok(restaurantApiService.getRestaurants());
  }

  @GetMapping("/restaurants/{id}/menu")
  public ResponseEntity<RestaurantMenu> getRestaurantMenu(@PathVariable Long id) {
    val res = restaurantApiService.getRestaurantById(id);
    if (res == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(mapper.map(res));
  }

  @GetMapping("/restaurants/{id}/details")
  public ResponseEntity<RestaurantDetails> getRestaurant(@PathVariable Long id) {
    val res = restaurantApiService.getRestaurantById(id);
    if (res == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(mapper.toDetails(res));
  }
}
