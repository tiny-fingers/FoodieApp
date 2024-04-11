package tinyfingers.simplilearn.foodieapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tinyfingers.simplilearn.foodieapp.filter.JwtRequestFilter;
import tinyfingers.simplilearn.foodieapp.service.UserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, HttpSecurity httpSecurity) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
//            .cors(httpSecurityCorsConfigurer ->
//                    httpSecurityCorsConfigurer.configure(httpSecurity.))
            .addFilterBefore(new JwtRequestFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
      .authorizeHttpRequests(authorize -> authorize
//              .requestMatchers("/api/**").permitAll()
              .requestMatchers("/api/orders/**", "/api/orders").authenticated()
              .anyRequest().permitAll())
      .sessionManagement(customSession -> customSession.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

    return http.build();
  }
}