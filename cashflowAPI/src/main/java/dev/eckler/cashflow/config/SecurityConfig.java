package dev.eckler.cashflow.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
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
                "https://cashflow.eckler",
                "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Profile("prod")
    public JwtDecoder jwtDecoder() {
        // Fetch JWKS keys via internal container network (no Traefik needed)
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withJwkSetUri(
                        "http://keycloak-auth:8080/realms/cashflow_realm/protocol/openid-connect/certs")
                .build();

        // But validate the issuer against the PUBLIC url (matches the token's iss
        // claim)
        OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators
                .createDefaultWithIssuer("https://keycloak.eckler/realms/cashflow_realm");

        decoder.setJwtValidator(issuerValidator);
        return decoder;
    }

    // @Bean
    // @Profile("dev")
    // public JwtDecoder jwtDecoderDev() {
    // NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders
    // .fromIssuerLocation(issuer);
    // jwtDecoder.setJwtValidator(jwt -> OAuth2TokenValidatorResult.success());
    // return jwtDecoder;
    // }
}
