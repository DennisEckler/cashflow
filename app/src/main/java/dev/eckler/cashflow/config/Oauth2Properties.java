package dev.eckler.cashflow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.security.oauth2.resourceserver.jwt")
public record Oauth2Properties(String issuerUri) {

}
