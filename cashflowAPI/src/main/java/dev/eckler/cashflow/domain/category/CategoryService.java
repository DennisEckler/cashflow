package dev.eckler.cashflow.domain.category;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.eckler.cashflow.domain.identifier.IdentifierService;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.CategoryResponse;

@Service
public class CategoryService {

  private final CategoryRepository cr;
  private final IdentifierService is;

  public CategoryService(CategoryRepository cr, IdentifierService is) {
    this.cr = cr;
    this.is = is;
  }

  public List<CategoryResponse> getCategoriesByUser(String userID) {
    List<CategoryResponse> categoryResponses = cr.findAllByUserID(userID).stream()
      .map(CategoryMapper::categoryToCategoryResponse)
      .toList();
    if (categoryResponses.isEmpty()){
      throw new CategoryNotFoundException(userID);
    }
    return categoryResponses;
  }

  public ResponseEntity<CategoryResponse> createCategory(CategoryCreateRequest categoryCreateRequest) {
    Category category = CategoryMapper.categoryCreateRequestToCategory(categoryCreateRequest);
    Category response = cr.save(category);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(CategoryMapper.categoryToCategoryResponse(response));
  }

  public ResponseEntity<Category> changeType(Category category, String userID) {
    Category dbCategory = cr.findByIdAndUserID(category.getId(), userID)
        .orElseThrow(() -> new CategoryNotFoundException(""));
    dbCategory.setType(category.getType());
    return ResponseEntity.ok(cr.save(dbCategory));
  }

  public ResponseEntity<String> deleteCategory(Long id, String userID) {
    Optional<Category> category = cr.findByIdAndUserID(id, userID);
    boolean allowDeleteUndefined = true;
    if (category.isPresent()) {
      category.get().getIdentifier()
          .forEach(identifier -> is.deleteIdentifier(identifier.getId(),
              allowDeleteUndefined));
      cr.delete(category.get());
      return ResponseEntity.ok(String.join(" ", "Category with id:", id.toString(), "deleted"));
    }
    throw new CategoryNotFoundException("Category with id not found");
  }

}
