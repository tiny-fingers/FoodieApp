package tinyfingers.simplilearn.foodieapp.service.externalapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Sellable {
  private String name;
  private String description;
  private String type;
  private Long id;
}
