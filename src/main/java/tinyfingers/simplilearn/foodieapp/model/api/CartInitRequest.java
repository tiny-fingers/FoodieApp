package tinyfingers.simplilearn.foodieapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartInitRequest {
  private Long restaurantId;
}
