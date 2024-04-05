package tinyfingers.simplilearn.foodieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tinyfingers.simplilearn.foodieapp.model.domain.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByUserIdOrSessionId(String userId, String sessionId);
}
