package tinyfingers.simplilearn.foodieapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateOrderRequest {
  private Long cartId;
}
