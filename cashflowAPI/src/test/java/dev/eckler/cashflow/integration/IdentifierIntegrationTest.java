package dev.eckler.cashflow.integration;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import dev.eckler.cashflow.container.CashflowContainer;
import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.domain.identifier.IdentifierRepository;
import dev.eckler.cashflow.model.TestJwtToken;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.IdentifierCreateRequest;
import dev.eckler.cashflow.util.TestTokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ CashflowContainer.class, TestTokenUtil.class })
public class IdentifierIntegrationTest {

    @Autowired
    @Lazy
    TestJwtToken testJwtToken;

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
    void clear() {
        cr.deleteAll();
        ir.deleteAll();
    }

    @Test
    void deleteIdentifier_whenIdentifierIsDefault_shouldThrowNotAcceptable() {
        CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
        given()
                .header("Authorization", "Bearer " + testJwtToken.getToken())
                .contentType(ContentType.JSON)
                .body(ccr)
                .when()
                .post("/category")
                .then()
                .statusCode(201);

        Category c = cr.findAll().get(0);

        given()
                .header("Authorization", "Bearer " + testJwtToken.getToken())
                .contentType(ContentType.JSON)
                .when()
                .delete("/category/{categoryID}/identifier/{identifierID}", c.getId(),
                        c.getIdentifier().stream().findFirst().get().getId())
                .then()
                .statusCode(406);
    }

    @Test
    void deleteIdentifier_whenValidRequest_shouldDelete() {
        CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
        given()
                .header("Authorization", "Bearer " + testJwtToken.getToken())
                .contentType(ContentType.JSON)
                .body(ccr)
                .when()
                .post("/category")
                .then()
                .statusCode(201);

        Category persistedCategory = cr.findAll().get(0);
        Identifier identifierToBeDeleted = new Identifier("deleteMe", persistedCategory);
        Identifier persistedIdentifier = ir.save(identifierToBeDeleted);

        assertEquals(1, cr.findAll().size());
        assertEquals(2, ir.findAll().size());

        given()
                .header("Authorization", "Bearer " + testJwtToken.getToken())
                .when()
                .delete("/category/{categoryID}/identifier/{identifierID}",
                        persistedCategory.getId(),
                        persistedIdentifier.getId())
                .then()
                .statusCode(204);

    }

}
