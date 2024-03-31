package tinyfingers.simplilearn.foodieapp.controller;


import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tinyfingers.simplilearn.foodieapp.mapper.RestaurantMapper;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantDetails;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantDto;
import tinyfingers.simplilearn.foodieapp.model.api.RestaurantMenu;
import tinyfingers.simplilearn.foodieapp.service.externalapi.RestaurantApiService;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Restaurant;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Sellable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class RestaurantsController {
  private final RestaurantApiService restaurantApiService;
  private final RestaurantMapper mapper;

  @GetMapping("/restaurants")
  public ResponseEntity<List<RestaurantDto>> getRestaurants(@RequestParam("search") @Nullable String searchTerm) {
    if (searchTerm != null) return getRestaurantsByKeyword(searchTerm);

    log.info("getRestaurants");

    List<RestaurantDto> restaurants = restaurantApiService.getRestaurants()
            .stream()
            .map(mapper::toRestaurantDto)
            .toList();

    return ResponseEntity.ok(restaurants);
  }

  private ResponseEntity<List<RestaurantDto>> getRestaurantsByKeyword(String searchTerm) {
//    val keywords = searchTerm.split("\\s+");
    val keywords = searchTerm.split("\\s*,\\s*");

    log.info("getRestaurantsByKeyword for keywords: ");
    Arrays.stream(keywords).toList()
            .stream()
            .map(String::toString)
            .forEach(log::info);

    val restaurants = restaurantApiService.findbyKeyword(List.of(keywords));
    val res = restaurants.stream().map(mapper::toRestaurantDto).toList();
    return ResponseEntity.ok(res);
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
