package dev.eckler.cashflow.integration;

import dev.eckler.cashflow.shared.CashflowConst;
import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.eckler.cashflow.domain.identifier.IdentifierRepository;
import dev.eckler.cashflow.container.CashflowContainer;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.shared.TransactionType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//- deleting identifier remains as null value in the Transactions

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(CashflowContainer.class)
public class CategoryIntegrationTest {

  static String CLIENT_ID = "cashflow";
  static String CLIENT_SECRET = "T9zHMs2YRIgy5mMckbBNALo1URv7Dp55";


  @Autowired
  OAuth2ResourceServerProperties oAuth2Properties;

  @LocalServerPort
  private int port;
  @Autowired
  CategoryRepository cr;
  @Autowired
  IdentifierRepository ir;


  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port + "/v1/api/";
  }

  @AfterEach
  void clear(){
    cr.deleteAll();
    ir.deleteAll();
  }


  @Test
  void createCategory_shouldBeCreatedWithInitialValuesAndDefaultIdentifier() {
    CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
    given()
      .header("Authorization", "Bearer " + getToken())
      .contentType(ContentType.JSON)
      .body(ccr)
      .when()
      .post("/category")
      .then()
      .statusCode(201)
      .body("id", notNullValue())
      .body("label", is("lebensmittel"))
      .body("userID", notNullValue())
      .body("type", is(TransactionType.FIXED.toString().toLowerCase()))
      .body("identifier[0].label", is(CashflowConst.UNDEFINED))
      ;
  }


  @Test
  void createCategory_whenAlreadyExist_shouldThrowConflict() {
    CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
    given()
      .header("Authorization", "Bearer " + getToken())
      .contentType(ContentType.JSON)
      .body(ccr)
      .when()
      .post("/category")
      .then()
      .statusCode(201);
    String s = given()
      .header("Authorization", "Bearer " + getToken())
      .contentType(ContentType.JSON)
      .body(ccr)
      .when()
      .post("/category")
      .then()
      .statusCode(409)
      .extract()
      .asPrettyString();
    System.out.println("gojo: " + s);
  }


  private String getToken() {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.put("grant_type", singletonList("client_credentials"));
    map.put("client_id", singletonList(CLIENT_ID));
    map.put("client_secret", singletonList(CLIENT_SECRET));

    String authServerUrl = oAuth2Properties.getJwt().getIssuerUri() +
        "/protocol/openid-connect/token";

    var request = new HttpEntity<>(map, httpHeaders);
    KeyCloakToken token = restTemplate.postForObject(
        authServerUrl,
        request,
        KeyCloakToken.class);

    assert token != null;
    return token.accessToken();
  }

  record KeyCloakToken(@JsonProperty("access_token") String accessToken) {
  }
}
