package tinyfingers.simplilearn.foodieapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.val;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

  private static final SecretKey key = Jwts.SIG.HS256.key().build();

  public static String generateToken(String username) {
    val secretKey = Keys.builder(key).build();
    return Jwts.builder()
            .signWith(secretKey)
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .compact();
  }

  public static Claims validateToken(String token) {
    val secretKey = Keys.builder(key).build();
    return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }
}
