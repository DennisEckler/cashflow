package dev.eckler.cashflow;

import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import com.fasterxml.jackson.annotation.JsonProperty;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//- deleting category should also delete all the identifier
//- deleting identifier remains as null value in the Transactions
//- creating category is created with initial values
//- check for unique category and identifier


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

  static String POSTGRES_IMAGE = "postgres:16-alpine";
  static String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:26.1.2";
  static String realmImportFile = "/cashflow_realm-realm.json";
  static String realmName = "cashflow_realm";
  static String CLIENT_ID = "cashflow";
  static String CLIENT_SECRET = "T9zHMs2YRIgy5mMckbBNALo1URv7Dp55";

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
  static KeycloakContainer keycloak = new KeycloakContainer(KEYCLOAK_IMAGE).withRealmImportFile(realmImportFile);

  @Autowired
  OAuth2ResourceServerProperties oAuth2Properties;

  @LocalServerPort
  private Integer port;
  //@Autowired
  //CategoryService cs;
  //@Autowired
  //CategoryRepository cr;

  @BeforeAll
  static void beforeAll(){
    postgres.start();
    keycloak.start();
  }

  @AfterAll
  static void afterAll(){
    postgres.stop();
    keycloak.stop();
  }

  @BeforeEach
  void setUp(){
    RestAssured.baseURI = "http://localhost:" + port + "/v1/api/";
    //cr.deleteAll();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry){
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    System.out.println("xsxsxs " + keycloak.getAuthServerUrl());
    registry.add(
      "spring.security.oauth2.resourceserver.jwt.issuer-uri",
      () -> keycloak.getAuthServerUrl() + "/realms/" + realmName
    );
  }

  @Test
  void test(){
    CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
    String token = getToken();
    given()
      .header("Authorization", "Bearer " + token)
      .contentType(ContentType.JSON)
        .body(ccr)
      .when()
        .post("/category")
      .then()
        .statusCode(201);
  }


private String getToken() {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.put("grant_type", singletonList("client_credentials"));
    map.put("client_id", singletonList(CLIENT_ID));
    map.put("client_secret", singletonList(CLIENT_SECRET));

    String authServerUrl =
      oAuth2Properties.getJwt().getIssuerUri() +
      "/protocol/openid-connect/token";

    var request = new HttpEntity<>(map, httpHeaders);
    KeyCloakToken token = restTemplate.postForObject(
      authServerUrl,
      request,
      KeyCloakToken.class
    );

    assert token != null;
    return token.accessToken();
  }

  record KeyCloakToken(@JsonProperty("access_token") String accessToken) {}
}
