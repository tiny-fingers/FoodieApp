package tinyfingers.simplilearn.foodieapp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {
  private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  public static String encode(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public static boolean validatePassword(String password, String encodedPassword) {
    return bCryptPasswordEncoder.matches(password, encodedPassword);
  }
}
