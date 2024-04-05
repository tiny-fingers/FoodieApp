package tinyfingers.simplilearn.foodieapp.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tinyfingers.simplilearn.foodieapp.model.domain.OrderItem;
import tinyfingers.simplilearn.foodieapp.model.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderAPI {
  private long id;
  private String restaurantName;
  private long restaurantId;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private List<OrderItem> orderItems = new ArrayList<>();
}
