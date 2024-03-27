package tinyfingers.simplilearn.foodieapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import tinyfingers.simplilearn.foodieapp.model.api.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
@Setter
@Getter
@ToString
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String userId;
  private long restaurantId;
  private LocalDateTime orderDate;
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus = OrderStatus.INITIATED;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<OrderItem> orderItems = new ArrayList<>();
}
