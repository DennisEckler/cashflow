package dev.eckler.cashflow.container;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

import dasniko.testcontainers.keycloak.KeycloakContainer;

@TestConfiguration(proxyBeanMethods = false)
public class CashflowContainer {

    static String POSTGRES_IMAGE = "postgres:16-alpine";
    static String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:26.4";
    static String realmImportFile = "/cashflow_realm-realm.json";
    static String realmName = "cashflow_realm";

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgres() {
        return new PostgreSQLContainer<>(POSTGRES_IMAGE);
    }

    @Bean
    KeycloakContainer keycloak() {
        KeycloakContainer keycloak = new KeycloakContainer(KEYCLOAK_IMAGE)
                .withRealmImportFile(realmImportFile);
        return keycloak;
    }

    @Bean
    DynamicPropertyRegistrar properties(KeycloakContainer keycloak) {
        return (registry) -> registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/" + realmName);

    }
}
