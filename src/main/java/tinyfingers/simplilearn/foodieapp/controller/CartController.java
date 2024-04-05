package tinyfingers.simplilearn.foodieapp.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import tinyfingers.simplilearn.foodieapp.service.CartService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class CartController {

  private final DomainApiMapper domainApiMapper;

  private final CartService cartService;

  @GetMapping("/cart")
  public ResponseEntity<CartAPI> getCart(@RequestHeader("userId") @Nullable String userId, HttpSession session) {
    String identifier = userId == null ? session.getId() : userId;

    log.info("Get cart for userId {} and sessionId {}. Use identifier {} ", userId, session.getId(), identifier);

    return ResponseEntity.ok(cartService.getCart(identifier));

  }

  @GetMapping("/carts")
  public ResponseEntity<List<CartAPI>> getAllCarts(HttpSession session) {
    return ResponseEntity.ok(cartService.getCarts().stream().map(domainApiMapper::map).toList());
  }

  @PostMapping("/cart")
  public ResponseEntity<CartAPI> createCart(@RequestBody CartAPI cartAPI, @RequestParam @Nullable String userId, HttpSession session) {
    if (StringUtils.isBlank(userId)) {
      cartAPI.setSessionId(session.getId());
    } else {
      cartAPI.setUserId(userId);
    }
    val identifier = StringUtils.isBlank(userId) ? session.getId() : userId;

    val cart = cartService.createCart(identifier, cartAPI);

    return ResponseEntity.ok(domainApiMapper.map(cart));
  }

  @DeleteMapping("/carts")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void clearCart(@RequestHeader("userId") @Nullable String userId, HttpSession session) {
    val identifier = StringUtils.isBlank(userId) ? session.getId() : userId;
    cartService.deleteCart(identifier);
  }
}
