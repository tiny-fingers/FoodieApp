package tinyfingers.simplilearn.foodieapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tinyfingers.simplilearn.foodieapp.model.domain.User;
import tinyfingers.simplilearn.foodieapp.repository.UserRepository;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserDetailsService {
  private final UserRepository userRepository;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getEncodedPassword(), new ArrayList<>());
  }
}
