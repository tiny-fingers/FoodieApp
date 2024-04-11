package tinyfingers.simplilearn.foodieapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemAPI {
  private Long menuItemId;
  private Integer quantity;
  private String name;
  private double unitPrice;
}
