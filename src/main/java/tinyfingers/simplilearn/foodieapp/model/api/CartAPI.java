package tinyfingers.simplilearn.foodieapp.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartAPI {
  private Long id;
  private Long restaurantId;
  private String restaurantName;
  private String userId;
  private String sessionId;
  private List<CartItemAPI> cartItems = new ArrayList<>();
  private Double totalPrice;
}
