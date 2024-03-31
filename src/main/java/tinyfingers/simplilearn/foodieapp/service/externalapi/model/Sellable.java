package tinyfingers.simplilearn.foodieapp.service.externalapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tinyfingers.simplilearn.foodieapp.model.util.CollectionToStringConverter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Sellable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private String sellableType;
  @ManyToOne(targetEntity = Restaurant.class, fetch = FetchType.LAZY)
  @JsonIgnore
  private Restaurant restaurant;
  @Column
  @Convert(converter = CollectionToStringConverter.class)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Set<String> keyword = new HashSet<>();
  @Column(precision = 10, scale = 2)
  private BigDecimal price;

  public Sellable(long id, String name, String description, String type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.sellableType = type;
  }
}
