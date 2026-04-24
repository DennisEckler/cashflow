package dev.eckler.cashflow.container;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class CashflowContainer {

    private static final String POSTGRES_IMAGE = "postgres:16-alpine";
    private static final String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:26.4";
    private static final String REALM_FILE = "/cashflow_realm-realm.json";
    private static final String REALM_NAME = "cashflow_realm";

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgres() {
        return new PostgreSQLContainer(POSTGRES_IMAGE);
    }

    @Bean
    KeycloakContainer keycloak() {
        return new KeycloakContainer(KEYCLOAK_IMAGE)
            .withRealmImportFile(REALM_FILE);
    }

    @Bean
    DynamicPropertyRegistrar properties(KeycloakContainer keycloak) {
        return registry -> registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
            () -> keycloak.getAuthServerUrl() + "/realms/" + REALM_NAME);

    }
}
