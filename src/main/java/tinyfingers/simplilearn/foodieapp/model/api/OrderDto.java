package tinyfingers.simplilearn.foodieapp.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tinyfingers.simplilearn.foodieapp.model.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
  private long id;
  private String restaurantName;
  private long restaurantId;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private List<OrderItem> orderItems = new ArrayList<>();
}
