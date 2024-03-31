package tinyfingers.simplilearn.foodieapp.service.externalapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tinyfingers.simplilearn.foodieapp.model.util.CollectionToStringConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name =  "restaurants")
public class Restaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long restaurantId;
  private String restaurantName;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
  private List<Sellable> sellable = new ArrayList<>();
  private String restaurantAddress;
  @Column
  @Convert(converter = CollectionToStringConverter.class)
  private Set<String> keyword = new HashSet<>();
}
