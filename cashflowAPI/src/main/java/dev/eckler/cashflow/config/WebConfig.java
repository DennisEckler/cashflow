package dev.eckler.cashflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v1/api/**")
                .allowedOrigins("https://cashflow.eckler", "http://localhost:4200")
                .allowedMethods("GET", "POST", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
