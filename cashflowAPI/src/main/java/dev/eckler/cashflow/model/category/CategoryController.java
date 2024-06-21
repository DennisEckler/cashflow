package dev.eckler.cashflow.model.category;

import static dev.eckler.cashflow.shared.CashflowConst.USER_ID;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {


  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/")
  public ResponseEntity<List<Category>> getCategories() {
    List<Category> categories = categoryService.getCategoriesByUser(USER_ID);
    HttpStatus status = categories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    return new ResponseEntity<>(categories, status);
  }

  @PostMapping("/")
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    category.setUserID(USER_ID);
    return categoryService.createCategory(category);
  }

  @PatchMapping("/")
  public ResponseEntity<Category> changeType(
      @RequestBody Category category) {
    return categoryService.changeType(category);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id) {
    return categoryService.deleteCategory(id, USER_ID);
  }


}
