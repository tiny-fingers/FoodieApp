package tinyfingers.simplilearn.foodieapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtUtilTest {
  @Test
  void testGenerateToken() {
    String username = "testUser";
    String token = JwtUtil.generateToken(username);

    // Verify that the token is not null and not empty
    assertNotNull(token);
    assertFalse(token.isEmpty());

    System.out.println(token);

//    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImlhdCI6MTcxMjU3ODY3MCwiZXhwIjoxNzEyNjY1MDcwfQ.xFYZTopn_LTxKZQwaNmV8gl3JJNWd61ykQIHznspph8";
    String decipheredUsername = JwtUtil.validateToken(token).getSubject();

    // Verify that the token is not null and not empty
    System.out.println(decipheredUsername);
  }
}