package dev.eckler.cashflow.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            @Qualifier("customCorsConfig") CorsConfigurationSource customCorsConfig)
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(customCorsConfig))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/v3/**", "/swagger-ui/**", "/h2/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    CorsConfigurationSource customCorsConfig() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "https://cashflow.eckler",
                "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
