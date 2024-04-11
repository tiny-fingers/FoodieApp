package tinyfingers.simplilearn.foodieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tinyfingers.simplilearn.foodieapp.model.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
