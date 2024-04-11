package tinyfingers.simplilearn.foodieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tinyfingers.simplilearn.foodieapp.model.domain.Sellable;

import java.util.List;

@Repository
public interface SellableRepository extends JpaRepository<Sellable, Long> {
  @Query("select s from Sellable s where s.restaurant.restaurantId = :restaurantId")
  List<Sellable> findAllByRestaurantId(Long restaurantId);
}
