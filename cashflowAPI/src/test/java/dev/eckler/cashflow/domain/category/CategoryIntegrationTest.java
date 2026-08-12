package dev.eckler.cashflow.domain.category;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.eckler.cashflow.base.BaseIntegrationTest;
import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.domain.identifier.IdentifierRepository;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.TransactionType;
import io.restassured.http.ContentType;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    CategoryRepository cr;
    @Autowired
    IdentifierRepository ir;


    @AfterEach
    void clear() {
        cr.deleteAll();
        ir.deleteAll();
    }

    @Test
    void createCategory_shouldBeCreatedWithInitialValuesAndDefaultIdentifier() {
        CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
        given()
            .header("Authorization", "Bearer " + "any")
            .contentType(ContentType.JSON)
            .body(ccr)
            .when()
            .post("/category")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("label", is("lebensmittel"))
            .body("userID", notNullValue())
            .body("type", is(TransactionType.IGNORE.toString()))
            .body("identifier[0].label", is("lebensmittel_" + Category.CATEGORY_LABEL_SUFFIX));
    }

    @Test
    void createCategory_whenAlreadyExist_shouldThrowConflict() {
        CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
        given()
            .header("Authorization", "Bearer " + "any")
            .contentType(ContentType.JSON)
            .body(ccr)
            .when()
            .post("/category")
            .then()
            .statusCode(201);
        given()
            .header("Authorization", "Bearer " + "any")
            .contentType(ContentType.JSON)
            .body(ccr)
            .when()
            .post("/category")
            .then()
            .statusCode(409);
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
        assertTrue(cr.findAll().isEmpty());
        assertTrue(ir.findAll().isEmpty());
    }

    private Category createIdentifierForCategory(Category category) {
        Set<Identifier> identifiers = category.getIdentifier();
        identifiers.add(new Identifier("handsome", category));
        identifiers.add(new Identifier("foo", category));
        category.setIdentifier(identifiers);
        return cr.save(category);
    }

}
