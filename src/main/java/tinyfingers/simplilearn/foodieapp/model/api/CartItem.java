package tinyfingers.simplilearn.foodieapp.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {
  private Long id;
  private Integer quantity;
  private String name;
  @JsonProperty("price")
  private double unitPrice;
}
