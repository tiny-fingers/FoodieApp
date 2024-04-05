package tinyfingers.simplilearn.foodieapp.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tinyfingers.simplilearn.foodieapp.mapper.DomainApiMapper;
import tinyfingers.simplilearn.foodieapp.model.api.CartAPI;
import tinyfingers.simplilearn.foodieapp.model.api.CartItemAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Cart;
import tinyfingers.simplilearn.foodieapp.model.domain.CartItem;
import tinyfingers.simplilearn.foodieapp.model.domain.Sellable;
import tinyfingers.simplilearn.foodieapp.repository.CartRepository;
import tinyfingers.simplilearn.foodieapp.repository.RestaurantsRepository;
import tinyfingers.simplilearn.foodieapp.repository.SellableRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class CartController {

  private static Map<String, Cart> carts = new HashMap<>();

  private final CartRepository cartRepository;
  private final SellableRepository sellableRepository;
  private final RestaurantsRepository restaurantsRepository;

  private final DomainApiMapper domainApiMapper;

  @GetMapping("/cart")
  public ResponseEntity<CartAPI> getCart(@RequestHeader("userId") @Nullable String userId, HttpSession session) {
    String identifier = userId == null ? session.getId() : userId;
    log.info("Get cart for userId {} and sessionId {}. Use identifier {} ", userId, session.getId(), identifier);

    val cart = cartRepository.findByUserIdOrSessionId(userId, session.getId());

    return cart
            .map(value -> {
              val api = domainApiMapper.map(value);
              val totalPrice = api.getCartItems().stream()
                      .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                      .sum();
              api.setTotalPrice(totalPrice);
              return ResponseEntity.ok(api);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

  }

  @GetMapping("/carts")
  public ResponseEntity<List<Cart>> getAllCarts(HttpSession session) {
    return ResponseEntity.ok(carts.values().stream().toList());
  }

  @PostMapping("/cart")
  @Transactional
  public ResponseEntity<CartAPI> createCart(@RequestBody CartAPI cartAPI, @RequestParam @Nullable String userId, HttpSession session) {
    if (StringUtils.isBlank(userId)) {
      cartAPI.setSessionId(session.getId());
    } else {
      cartAPI.setUserId(userId);
    }

    val restaurantId = cartAPI.getRestaurantId();
    val sellables = sellableRepository.findAllByRestaurantId(restaurantId);
    if (!allCartItemsContained(cartAPI.getCartItems(), sellables)) return ResponseEntity.badRequest().build();

    val cart = domainApiMapper.map(cartAPI);
    cartRepository
            .findByUserIdOrSessionId(userId, session.getId())
            .ifPresent(existingCart -> cart.setId(existingCart.getId()));
    val cartItems = cartAPI.getCartItems()
            .stream()
            .map(cartItemAPI -> {
              CartItem cartItem = domainApiMapper.map(cartItemAPI);
              Sellable sellable = sellables.stream()
                      .filter(s -> s.getId().equals(cartItemAPI.getMenuItemId()))
                      .findFirst()
                      .orElseThrow();
              cartItem.setMenuItem(sellable);
              cartItem.setName(sellable.getName());
              return cartItem;
            })
            .toList();

    cart.setRestaurant(restaurantsRepository.findById(restaurantId).orElseThrow());
    cart.setCartItems(cartItems);

    cartRepository.save(cart);

    return ResponseEntity.ok(domainApiMapper.map(cart));
  }

  private boolean allCartItemsContained(List<CartItemAPI> cartItems, List<Sellable> sellables) {
    return cartItems
            .stream()
            .allMatch(cartItem -> sellables.stream()
                    .anyMatch(sellable -> sellable.getId().equals(cartItem.getMenuItemId())));
  }

  @PostMapping("/carts/clear")
  @ResponseStatus(HttpStatus.OK)
  public void clearCart() {
    carts.clear();
  }
}
