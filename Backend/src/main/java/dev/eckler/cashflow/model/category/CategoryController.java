package dev.eckler.cashflow.model.category;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  private CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories/{userID}")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public ResponseEntity<List<Category>> getCategories(@PathVariable @NotNull String userID) {
    List<Category> categories = categoryService.getAllCategories(userID);
    HttpStatus status = categories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    return new ResponseEntity<>(categories, status);
  }

  @GetMapping("/custom")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public ResponseEntity<List<Category>> getWithCustomQuery() {
    List<Category> categories = categoryService.customJoin();
    HttpStatus status = categories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    return new ResponseEntity<>(categories, status);
  }


}
