package dev.eckler.cashflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .cors(cors -> cors.configurationSource(customCorsConfig()))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**"))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/v3/**", "/swagger-ui/**", "/h2/**").permitAll()
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    UrlBasedCorsConfigurationSource customCorsConfig() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "https://cashflow.eckler.dev",
            "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    @Profile("prod")
//    public JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder decoder = NimbusJwtDecoder
//            .withJwkSetUri(
//                "http://keycloak-auth:8080/realms/cashflow_realm/protocol/openid-connect/certs")
//            .build();
//
//        OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators
//            .createDefaultWithIssuer("https://keycloak.eckler.dev/realms/cashflow_realm");
//        decoder.setJwtValidator(issuerValidator);
//        return decoder;
//    }
}
