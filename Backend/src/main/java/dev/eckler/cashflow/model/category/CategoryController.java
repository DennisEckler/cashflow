package dev.eckler.cashflow.model.category;

import static dev.eckler.cashflow.jwt.CustomJwt.getUserId;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  private final CategoryService categoryService;
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories/{userID}")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public ResponseEntity<List<Category>> getCategories(@PathVariable @NotNull String userID) {
    List<Category> categories = categoryService.getCategoriesByUser(userID);
    HttpStatus status = categories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    return new ResponseEntity<>(categories, status);
  }

  @PostMapping("/save-categories")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public ResponseEntity<?> saveCategories(
      @RequestHeader("Authorization") String bearerRequest,
      @RequestBody List<Category> categories) {
    String userID = getUserId(bearerRequest, issuer);
    categories.stream().filter(category -> category.getUserID() == null)
        .forEach(category -> category.setUserID(userID));
    categoryService.saveCategories(categories);
    return new ResponseEntity<>("Categories are saved", HttpStatus.OK);
  }

  @DeleteMapping("/category/delete/{categoryID}")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public ResponseEntity<?> deleteCategory(@PathVariable(name = "categoryID") Long categoryID) {
    if (categoryService.deleteCategory(categoryID)) {
      return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }
    return new ResponseEntity<>("Cant find Category", HttpStatus.NOT_FOUND);
  }


}
