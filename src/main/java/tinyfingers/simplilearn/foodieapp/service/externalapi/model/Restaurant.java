package tinyfingers.simplilearn.foodieapp.service.externalapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Restaurant {
  private String restaurantName;
  private long restaurantId;
  private List<Sellable> sellable = new ArrayList<>();
  private String restaurantAddress;
}
