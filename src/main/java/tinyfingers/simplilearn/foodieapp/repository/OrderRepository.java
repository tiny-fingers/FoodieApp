package tinyfingers.simplilearn.foodieapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tinyfingers.simplilearn.foodieapp.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  @EntityGraph(attributePaths = {"orderItems"})
  List<Order> findAll();
}
