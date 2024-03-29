package tinyfingers.simplilearn.foodieapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**")
              .permitAll()
              .anyRequest().permitAll())
      .sessionManagement(customSession -> customSession.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
    return http.build();
  }

}