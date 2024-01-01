package dev.eckler.cashflow.config;

import dev.eckler.cashflow.jwt.CustomJwt;
import dev.eckler.cashflow.jwt.CustomJwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
//        allows connection to h2 for all
//        .authorizeHttpRequests(customizer -> customizer.requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**")).permitAll())
//        .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2/**")))
//        .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));

//    activate to authorize with token
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(
            jwt -> jwt.jwtAuthenticationConverter(customJwtConverter())));
    return http.build();
  }

  @Bean
  public Converter<Jwt, CustomJwt> customJwtConverter() {
    return new CustomJwtConverter();
  }
}
