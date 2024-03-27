package tinyfingers.simplilearn.foodieapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import tinyfingers.simplilearn.foodieapp.model.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@ToString
public class CreateOrderRequest {
  private long restaurantId;
  private LocalDateTime orderDate;
  private List<OrderItem> orderItems = new ArrayList<>();
}
