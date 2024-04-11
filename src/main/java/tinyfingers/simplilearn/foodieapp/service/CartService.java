package tinyfingers.simplilearn.foodieapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.exception.ResourceNotFoundException;
import tinyfingers.simplilearn.foodieapp.mapper.DomainApiMapper;
import tinyfingers.simplilearn.foodieapp.model.api.CartAPI;
import tinyfingers.simplilearn.foodieapp.model.api.CartItemAPI;
import tinyfingers.simplilearn.foodieapp.model.domain.Cart;
import tinyfingers.simplilearn.foodieapp.model.domain.CartItem;
import tinyfingers.simplilearn.foodieapp.model.domain.Sellable;
import tinyfingers.simplilearn.foodieapp.repository.CartRepository;
import tinyfingers.simplilearn.foodieapp.repository.RestaurantsRepository;
import tinyfingers.simplilearn.foodieapp.repository.SellableRepository;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;

@RequiredArgsConstructor
@Slf4j
@Service
public class CartService {

  private final CartRepository cartRepository;
  private final SellableRepository sellableRepository;
  private final RestaurantsRepository restaurantsRepository;

  private final DomainApiMapper domainApiMapper;

  public CartAPI getCart(String identifier) {
    return cartRepository.findByUserIdOrSessionId(identifier, identifier)
            .map(value -> {
              val cartAPI = domainApiMapper.map(value);
              val totalPrice = calculateTotalPrice(cartAPI);
              cartAPI.setTotalPrice(totalPrice);
              return cartAPI;
            })
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
  }

  public List<Cart> getCarts() {
    return cartRepository.findAll();
  }

  public void deleteCart(String identifier) {
    cartRepository.deleteByUserIdOrSessionId(identifier);
  }

  public CartAPI createCart(String identifier, CartAPI cartAPI) {

    val restaurantId = cartAPI.getRestaurantId();
    val sellables = sellableRepository.findAllByRestaurantId(restaurantId);
    if (!allCartItemsContained(cartAPI.getCartItems(), sellables)) throw new ResourceNotFoundException("Cart not found");

    val cart = domainApiMapper.map(cartAPI);
    cartRepository
            .findByUserIdOrSessionId(identifier, identifier)
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

    val res = domainApiMapper.map(cartRepository.save(cart));
    val totalPrice = calculateTotalPrice(res);
    res.setTotalPrice(totalPrice);

    return res;
  }
  
  public CartAPI initCart(Long restaurantId, String userId, String sessionId) {
    val cart = new Cart();
    cart.setRestaurant(restaurantsRepository.getReferenceById(restaurantId));
    cart.setSessionId(sessionId);
    cart.setUserId(userId);

    val identifier = isBlank(userId) ? sessionId : userId;
    cartRepository.deleteByUserIdOrSessionId(identifier);

    val cartAPI = domainApiMapper.map(cartRepository.save(cart));

    val totalPrice = calculateTotalPrice(cartAPI);
    cartAPI.setTotalPrice(totalPrice);

    return cartAPI;
  }

  public void deleteCart(Long cartId) {
    cartRepository.deleteById(cartId);

  }

  private Double calculateTotalPrice(CartAPI cartAPI) {
    return cartAPI.getCartItems()
            .stream()
            .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
            .sum();
  }

  private boolean allCartItemsContained(List<CartItemAPI> cartItems, List<Sellable> sellables) {
    return cartItems
            .stream()
            .allMatch(cartItem -> sellables.stream()
                    .anyMatch(sellable -> sellable.getId().equals(cartItem.getMenuItemId())));
  }
}
