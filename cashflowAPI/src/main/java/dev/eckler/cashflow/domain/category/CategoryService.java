package dev.eckler.cashflow.domain.category;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.eckler.cashflow.domain.identifier.IdentifierService;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final IdentifierService identifierService;

  public CategoryService(CategoryRepository categoryRepository,
      IdentifierService identifierService) {
    this.categoryRepository = categoryRepository;
    this.identifierService = identifierService;
  }

  public List<Category> getCategoriesByUser(String userID) {
    return categoryRepository.findAllByUserID(userID);
  }

  public ResponseEntity<Category> createCategory(Category category) {
    Category response = categoryRepository.save(category);
    return response.getId() != null ? ResponseEntity.ok(response)
        : ResponseEntity.badRequest().build();
  }

  public ResponseEntity<Category> changeType(Category category, String userID) {
    Category dbCategory = categoryRepository.findByIdAndUserID(category.getId(), userID)
        .orElseThrow(() -> new CategoryNotFoundException(""));
    dbCategory.setType(category.getType());
    return ResponseEntity.ok(categoryRepository.save(dbCategory));
  }

  public ResponseEntity<String> deleteCategory(Long id, String userID) {
    Optional<Category> category = categoryRepository.findByIdAndUserID(id, userID);
    boolean allowDeleteUndefined = true;
    if (category.isPresent()) {
      category.get().getIdentifier()
          .forEach(identifier -> identifierService.deleteIdentifier(identifier.getId(),
              allowDeleteUndefined));
      categoryRepository.delete(category.get());
      return ResponseEntity.ok(String.join(" ", "Category with id:", id.toString(), "deleted"));
    }
    throw new CategoryNotFoundException("Category with id not found");
  }

}
