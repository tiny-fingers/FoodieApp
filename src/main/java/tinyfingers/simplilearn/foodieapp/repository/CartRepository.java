package tinyfingers.simplilearn.foodieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tinyfingers.simplilearn.foodieapp.model.domain.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByUserIdOrSessionId(String userId, String sessionId);

  @Modifying
  @Transactional
  @Query("DELETE FROM Cart c WHERE c.userId = :identifier OR c.sessionId = :identifier")
  void deleteByUserIdOrSessionId(String identifier);
}
