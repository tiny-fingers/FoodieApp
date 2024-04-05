package tinyfingers.simplilearn.foodieapp.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tinyfingers.simplilearn.foodieapp.model.domain.Sellable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantAPI {
  private long restaurantId;
  private List<Sellable> menu = new ArrayList<>();
  private RestaurantDetails restaurantDetails;

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class RestaurantDetails {
    private String name;
    private String restaurantAddress;
  }
}
