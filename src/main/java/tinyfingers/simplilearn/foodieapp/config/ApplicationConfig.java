package tinyfingers.simplilearn.foodieapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class ApplicationConfig implements WebMvcConfigurer {

  @Value("${application.client-origin-url}")
  private String clientOriginUrl;

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins(clientOriginUrl)
//            .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.COOKIE)
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .maxAge(86400);
  }

}
