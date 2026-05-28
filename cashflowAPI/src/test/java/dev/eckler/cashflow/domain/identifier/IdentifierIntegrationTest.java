package dev.eckler.cashflow.domain.identifier;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.eckler.cashflow.base.BaseIntegrationTest;
import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class IdentifierIntegrationTest extends BaseIntegrationTest {

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
    void deleteIdentifier_whenIdentifierIsDefault_shouldThrowNotAcceptable() {
        CategoryCreateRequest ccr = new CategoryCreateRequest("lebensmittel");
        given()
            .header("Authorization", "Bearer " + "any")
            .contentType(ContentType.JSON)
            .body(ccr)
            .when()
            .post("/category")
            .then()
            .statusCode(201);

        Category c = cr.findAll().getFirst();

        given()
            .header("Authorization", "Bearer " + "any")
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
            .header("Authorization", "Bearer " + "any")
            .contentType(ContentType.JSON)
            .body(ccr)
            .when()
            .post("/category")
            .then()
            .statusCode(201);

        Category persistedCategory = cr.findAll().getFirst();
        Identifier identifierToBeDeleted = new Identifier("deleteMe", persistedCategory);
        Identifier persistedIdentifier = ir.save(identifierToBeDeleted);

        assertEquals(1, cr.findAll().size());
        assertEquals(2, ir.findAll().size());

        given()
            .header("Authorization", "Bearer " + "any")
            .when()
            .delete("/category/{categoryID}/identifier/{identifierID}",
                persistedCategory.getId(),
                persistedIdentifier.getId())
            .then()
            .statusCode(204);

    }

}
