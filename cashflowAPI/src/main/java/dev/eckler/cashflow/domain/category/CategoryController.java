package dev.eckler.cashflow.domain.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.eckler.cashflow.domain.util.JwtUtil;
import dev.eckler.cashflow.openapi.api.CategoryApi;
import dev.eckler.cashflow.openapi.model.CashflowErrorResponse;
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

  @ExceptionHandler({CategoryNotFoundException.class})
  public ResponseEntity<CashflowErrorResponse> error(CategoryNotFoundException ex){
    CashflowErrorResponse error = new CashflowErrorResponse();
    error.setStatusCode(HttpStatus.NOT_FOUND.value());
    error.setMessage(ex.getMessage());
    return ResponseEntity.status(error.getStatusCode()).body(error);
  }
  

  @Override
  public ResponseEntity<List<CategoryResponse>> getCategories(){
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Get Categories as user: {}", userID);
    List<CategoryResponse> categories = categoryService.getCategoriesByUser(userID);
    return ResponseEntity.ok(categories);
  }
  
  @Override
  public ResponseEntity<CategoryResponse> addCategory(@Valid CategoryCreateRequest categoryCreateRequest) {
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Creating Category: {} for userID: {}", categoryCreateRequest.getLabel(), userID);
    categoryCreateRequest.setUserID(userID);
    CategoryResponse response = categoryService.createCategory(categoryCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Override
  public ResponseEntity<Void> deleteCatgory(Long id) {
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Delete Category with ID: {} and userID: {}", id, userID);
    categoryService.deleteCategory(id, userID);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<CategoryResponse> updateCategory(Long id, @Valid CategoryUpdateRequest categoryUpdateRequest) {
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Update Category with ID: {} and userID: {}", id, userID);
    CategoryResponse response = categoryService.changeType(categoryUpdateRequest, userID);
    return ResponseEntity.ok(response);
  }

}
