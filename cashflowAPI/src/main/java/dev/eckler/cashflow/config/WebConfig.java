package dev.eckler.cashflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Service
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins("http://eckler.local:4200", "http://localhost:4200")
        .allowedMethods("GET", "POST", "DELETE", "PATCH")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
