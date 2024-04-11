package tinyfingers.simplilearn.foodieapp.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tinyfingers.simplilearn.foodieapp.model.domain.Restaurant;
import tinyfingers.simplilearn.foodieapp.model.domain.Sellable;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

  default List<Restaurant> findAllWithKeyword(List<String> keywords) {
    return findAll((Specification<Restaurant>) (root, query, criteriaBuilder) -> {
      query.distinct(true);

      List<Predicate> predicates = new ArrayList<>();

      Join<Restaurant, Sellable> sellable = root.join("sellable");

      for (String keyword : keywords) {
        predicates.add(criteriaBuilder.like(root.get("keyword"), "%" + keyword + "%"));
      }

      for (String keyword : keywords) {
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("restaurantName")), "%" + keyword.toLowerCase() + "%"));
      }

      for (String keyword : keywords) {
        predicates.add(criteriaBuilder.like(sellable.get("keyword"), "%" + keyword+ "%"));
      }

      return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    });
  }
}
