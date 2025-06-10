package dev.eckler.cashflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("v3/**", "swagger-ui/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2/**", "/v3/**", "/swagger-ui.html");
    }

    @Bean
    @Profile("prod")
    public JwtDecoder jwtDecoderProd() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders
                .fromIssuerLocation("http://192.168.2.65:9000/realms/cashflow_realm");
        jwtDecoder.setJwtValidator(jwt -> OAuth2TokenValidatorResult.success());
        return jwtDecoder;
    }

    @Bean
    @Profile("dev")
    public JwtDecoder jwtDecoderDev() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders
                .fromIssuerLocation("http://192.168.2.65:9000/realms/cashflow_realm");
        jwtDecoder.setJwtValidator(jwt -> OAuth2TokenValidatorResult.success());
        return jwtDecoder;
    }
}
