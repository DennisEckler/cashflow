package dev.eckler.cashflow.domain.category;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.eckler.cashflow.domain.util.JwtUtil;
import dev.eckler.cashflow.openapi.api.CategoryApi;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.CategoryResponse;
import dev.eckler.cashflow.openapi.model.CategoryUpdateRequest;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/v1/api")
@Log4j2
public class CategoryController implements CategoryApi {

  private final CategoryService categoryService;
  private final JwtUtil jwtUtil;

  public CategoryController(CategoryService categoryService, JwtUtil jwtUtil) {
    this.categoryService = categoryService;
    this.jwtUtil = jwtUtil;
  }

  //@ExceptionHandler({CategoryNotFoundException.class})
  //public CashflowErrorResponse error(CategoryNotFoundException ex){
  //  CashflowErrorResponse error = new CashflowErrorResponse();
  //  error.setStatusCode(ex.hashCode());
  //  error.setMessage(ex.getMessage());
  //  return error;
  //
  //}
  

  @Override
  public ResponseEntity<List<CategoryResponse>> getCategories(){
    log.debug("Get Categories");
    String userID = jwtUtil.readSubjectFromSecurityContext();
    List<CategoryResponse> categories = categoryService.getCategoriesByUser(userID);
    return ResponseEntity.ok(categories);
  }
  
  @Override
  public ResponseEntity<CategoryResponse> addCategory(@Valid CategoryCreateRequest categoryCreateRequest) {
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Creating Category: {} for userID: {}", categoryCreateRequest.getLabel(), userID);
    categoryCreateRequest.setUserID(userID);
    return categoryService.createCategory(categoryCreateRequest);
  }

  //
  //@Override
  //public ResponseEntity<Void> deleteCatgory(Long id) {
  //  // TODO Auto-generated method stub
  //  return CategoryApi.super.deleteCatgory(id);
  //}
  //
  //
  //@Override
  //public ResponseEntity<CategoryResponse> updateCategory(Long id, @Valid CategoryUpdateRequest categoryUpdateRequest) {
  //  // TODO Auto-generated method stub
  //  return CategoryApi.super.updateCategory(id, categoryUpdateRequest);
  //}

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
