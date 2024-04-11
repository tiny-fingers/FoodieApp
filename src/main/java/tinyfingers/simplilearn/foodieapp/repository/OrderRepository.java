package tinyfingers.simplilearn.foodieapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tinyfingers.simplilearn.foodieapp.model.domain.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  @EntityGraph(attributePaths = {"orderItems"})
  List<Order> findAll();

  @Query("SELECT o FROM Order o JOIN FETCH o.orderItems i JOIN FETCH i.menuItem m WHERE o.id = :id")
  Optional<Order> findById(Long id);

  @Query("SELECT o FROM Order o JOIN FETCH o.orderItems i JOIN FETCH i.menuItem m WHERE o.userId = :userId")
  List<Order> findAllByUserId(String userId);
}
