package dev.eckler.myDataAuthorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import dev.eckler.myDataAuthorization.configuration.KeycloakServerProperties;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties({ KeycloakServerProperties.class })
public class MyDataAuthorizationApplication {

  private static final Logger logger = LoggerFactory.getLogger(MyDataAuthorizationApplication.class);

  public static void main(String[] args) throws Exception {
    SpringApplication.run(MyDataAuthorizationApplication.class, args);
  }

  @Bean
  ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties,
      KeycloakServerProperties keycloakServerProperties) {

    return (evt) -> {

      Integer port = serverProperties.getPort();
      String keycloakContextPath = keycloakServerProperties.getContextPath();

      logger.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak", port, keycloakContextPath);
    };
  }

}
