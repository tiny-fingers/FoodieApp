package tinyfingers.simplilearn.foodieapp.controller;


import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tinyfingers.simplilearn.foodieapp.model.api.Cart;
import tinyfingers.simplilearn.foodieapp.model.api.CartItem;

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

  @GetMapping("/carts/{cardId}")
  public ResponseEntity<Cart> getCart(@PathVariable("cardId") String cardId, @RequestParam @Nullable String userId, HttpSession session) {
    String identifier = userId == null ? session.getId() : userId;
    log.info("Get cart with id {} for userId {} and sessionId {}. Use identifier {} ", cardId, userId, session.getId(), identifier);

    if (!carts.isEmpty()) {
      return ResponseEntity.ok(carts.get(identifier));
    }

    val cart = new Cart();
    val cartItems = List.of(
            new CartItem(1L, 1, "Pizza", 6.90),
            new CartItem(2L, 2, "Coca-Cola", 2.50));
    cart.setCardItems(cartItems);
    cart.setUserId("user1");

    return ResponseEntity.ok(cart);
  }

  @GetMapping("/carts")
  public ResponseEntity<List<Cart>> getAllCarts(HttpSession session) {
    return ResponseEntity.ok(carts.values().stream().toList());
  }

  @PostMapping("/cart")
  public ResponseEntity<Cart> createCart(@RequestBody Cart cart, @RequestParam @Nullable String userId, HttpSession session) {
    if (userId != null && !userId.isBlank()) {
      cart.setSessionId(session.getId());
    } else {
      cart.setUserId(userId);
    }
    String identifier = userId == null ? session.getId() : userId;
    carts.put(identifier, cart);
    log.info("Registered cart {} for identifier {}", cart, identifier);
    return ResponseEntity.ok(cart);
  }

  @PostMapping("/carts/clear")
  @ResponseStatus(HttpStatus.OK)
  public void clearCart() {
    carts.clear();
  }
}
