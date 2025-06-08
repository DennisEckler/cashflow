package dev.eckler.cashflow.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import dev.eckler.cashflow.container.CashflowContainer;
import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.domain.identifier.IdentifierRepository;
import dev.eckler.cashflow.model.TestJwtToken;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.TransactionType;
import dev.eckler.cashflow.shared.CashflowConst;
import dev.eckler.cashflow.util.TestTokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ CashflowContainer.class, TestTokenUtil.class })
public class CategoryIntegrationTest {

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
    void createCategory_shouldBeCreatedWithInitialValuesAndDefaultIdentifier() {
        CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
        given()
                .header("Authorization", "Bearer " + testJwtToken.getToken())
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
                .body("identifier[0].label", is(CashflowConst.UNDEFINED));
    }

    @Test
    void createCategory_whenAlreadyExist_shouldThrowConflict() {
        CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
        given()
                .header("Authorization", "Bearer " + testJwtToken.getToken())
                .contentType(ContentType.JSON)
                .body(ccr)
                .when()
                .post("/category")
                .then()
                .statusCode(201);
        String s = given()
                .header("Authorization", "Bearer " + testJwtToken.getToken())
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

    @Test
    void deleteCategory_ShouldAlsoDeleteAssociatedIdentifier() {
        Category category = new Category("label", "userID");
        category = cr.save(category);
        assertEquals(1, cr.findAll().size());
        assertEquals(1, ir.findAll().size());

        category = createIdentifierForCategory(category);
        assertEquals(3, ir.findAll().size());

        cr.deleteById(category.getId());
        assertTrue(cr.findAll().size() == 0);
        assertTrue(ir.findAll().size() == 0);
    }

    private Category createIdentifierForCategory(Category category) {
        Set<Identifier> identifiers = category.getIdentifier();
        identifiers.add(new Identifier("handsome", category));
        identifiers.add(new Identifier("foo", category));
        category.setIdentifier(identifiers);
        return cr.save(category);
    }

}
