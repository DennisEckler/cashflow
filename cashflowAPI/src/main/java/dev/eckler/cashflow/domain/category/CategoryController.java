package dev.eckler.cashflow.domain.category;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<Category>> getCategories(@AuthenticationPrincipal Jwt jwt)
      throws StreamReadException, IOException {
    String userID = jwt.getSubject();
    List<Category> categories = categoryService.getCategoriesByUser(userID);
    HttpStatus status = categories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    return new ResponseEntity<>(categories, status);
  }

  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody Category category, @AuthenticationPrincipal Jwt jwt) {
    String userID = jwt.getSubject();
    category.setUserID(userID);
    return categoryService.createCategory(category);
  }

  @PatchMapping
  public ResponseEntity<Category> changeType(
      @RequestBody Category category, @AuthenticationPrincipal Jwt jwt) {
    String userID = jwt.getSubject();
    return categoryService.changeType(category, userID);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id, @AuthenticationPrincipal Jwt jwt) {
    String userID = jwt.getSubject();
    return categoryService.deleteCategory(id, userID);
  }

}
