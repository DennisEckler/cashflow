package dev.eckler.cashflow.model.category;

import static dev.eckler.cashflow.jwt.CustomJwt.getUserId;

import dev.eckler.cashflow.config.Oauth2Properties;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/category")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasAuthority('ROLE_user')")
public class CategoryController {

  private final Oauth2Properties oauthProperties;

  private final CategoryService categoryService;

  public CategoryController(Oauth2Properties oauthProperties, CategoryService categoryService) {
    this.oauthProperties = oauthProperties;
    this.categoryService = categoryService;
  }

  @GetMapping("/")
  public ResponseEntity<List<Category>> getCategories(
      @RequestHeader("Authorization") String bearerRequest) {
    String userID = getUserId(bearerRequest, oauthProperties.issuerUri());
    List<Category> categories = categoryService.getCategoriesByUser(userID);
    HttpStatus status = categories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    return new ResponseEntity<>(categories, status);
  }

  @PostMapping("/")
  public ResponseEntity<Category> createCategory(
      @RequestHeader("Authorization") String bearerRequest,
      @RequestBody Category category) {
    String userID = getUserId(bearerRequest, oauthProperties.issuerUri());
    category.setUserID(userID);
    return categoryService.createCategory(category);
  }

  @PatchMapping("/")
  public ResponseEntity<Category> changeType(
      @RequestHeader("Authorization") String bearerRequest,
      @RequestBody Category category) {
    return categoryService.changeType(category);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(
      @PathVariable(name = "id") Long id,
      @RequestHeader("Authorization") String bearerRequest) {
    String userID = getUserId(bearerRequest, oauthProperties.issuerUri());
    return categoryService.deleteCategory(id, userID);
  }


}
