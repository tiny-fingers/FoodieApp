package tinyfingers.simplilearn.foodieapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tinyfingers.simplilearn.foodieapp.exception.InvalidUserException;
import tinyfingers.simplilearn.foodieapp.model.api.AuthenticationResponse;
import tinyfingers.simplilearn.foodieapp.model.api.Credentials;
import tinyfingers.simplilearn.foodieapp.model.domain.User;
import tinyfingers.simplilearn.foodieapp.repository.UserRepository;
import tinyfingers.simplilearn.foodieapp.util.JwtUtil;
import tinyfingers.simplilearn.foodieapp.util.PasswordEncoderUtil;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
  private final UserRepository userRepository;

  @PostMapping("/login")
  public  ResponseEntity<AuthenticationResponse> login(@RequestBody Credentials credentials) {
    log.info("login");
    val token =  JwtUtil.generateToken(credentials.getUsername());

    val encodedPassword = userRepository.findByUsername(credentials.getUsername()).getEncodedPassword();
    if (PasswordEncoderUtil.validatePassword(credentials.getPassword(), encodedPassword)) {
      return ResponseEntity.ok(new AuthenticationResponse(token));
    } else throw new InvalidUserException();
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.OK)
  public void signup(@RequestBody Credentials credentials) {
    log.info("signup");
    val encodedPassword = PasswordEncoderUtil.encode(credentials.getPassword());
    val user = new User();
    user.setUsername(credentials.getUsername());
    user.setEncodedPassword(encodedPassword);
    userRepository.save(user);
  }
}
