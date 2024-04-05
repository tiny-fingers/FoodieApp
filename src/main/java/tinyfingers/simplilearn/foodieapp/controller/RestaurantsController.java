package tinyfingers.simplilearn.foodieapp.controller;


import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tinyfingers.simplilearn.foodieapp.mapper.RestaurantMapper;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantAPI;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantMenuAPI;
import tinyfingers.simplilearn.foodieapp.service.RestaurantService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class RestaurantsController {
  private final RestaurantService restaurantService;
  private final RestaurantMapper mapper;

  @GetMapping("/restaurants")
  public ResponseEntity<List<RestaurantAPI>> getRestaurants(@RequestParam("search") @Nullable String searchTerm) {
    if (searchTerm != null) return getRestaurantsByKeyword(searchTerm);

    log.info("getRestaurants");

    List<RestaurantAPI> restaurants = restaurantService.getRestaurants()
            .stream()
            .map(mapper::toRestaurantDto)
            .toList();

    return ResponseEntity.ok(restaurants);
  }

  private ResponseEntity<List<RestaurantAPI>> getRestaurantsByKeyword(String searchTerm) {
    val keywords = searchTerm.split("\\s*,\\s*");

    log.info("getRestaurantsByKeyword for keywords: ");
    Arrays.stream(keywords).toList()
            .stream()
            .map(String::toString)
            .forEach(log::info);

    val restaurants = restaurantService.findByKeyword(List.of(keywords));
    val res = restaurants.stream().map(mapper::toRestaurantDto).toList();
    return ResponseEntity.ok(res);
  }

  @GetMapping("/restaurants/{id}/menu")
  public ResponseEntity<RestaurantMenuAPI> getRestaurantMenu(@PathVariable Long id) {
    val res = restaurantService.getRestaurantById(id);
    if (res == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(mapper.map(res));
  }

  @GetMapping("/restaurants/{id}/details")
  public ResponseEntity<RestaurantAPI.RestaurantDetails> getRestaurant(@PathVariable Long id) {
    val res = restaurantService.getRestaurantById(id);
    if (res == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(mapper.toDetails(res));
  }
}
