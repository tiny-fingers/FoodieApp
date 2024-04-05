package tinyfingers.simplilearn.foodieapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellableAPI {
  private Long id;
  private String name;
  private String description;
  private String sellableType;
  private BigDecimal price;
}
