package tinyfingers.simplilearn.foodieapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tinyfingers.simplilearn.foodieapp.service.externalapi.model.Sellable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantDto {
  private long restaurantId;
  private List<Sellable> menu = new ArrayList<>();
  private RestaurantDetails restaurantDetails;
}
