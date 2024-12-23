package dev.eckler.cashflow.domain.category;

import static dev.eckler.cashflow.shared.CashflowConst.UNDEFINED;

import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.domain.identifier.IdentifierService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    Set<Identifier> identifier = category.getIdentifier();
    identifier.add(new Identifier(UNDEFINED, category));
    category.setIdentifier(identifier);
    Category response = categoryRepository.save(category);
    return response.getId() != null ? ResponseEntity.ok(response)
        : ResponseEntity.badRequest().build();
  }

  public ResponseEntity<Category> changeType(Category category) {
    Category dbCategory = categoryRepository.findById(category.getId())
        .orElseThrow(() -> new CategoryNotFoundException(""));
    dbCategory.setType(category.getType());
    return ResponseEntity.ok(categoryRepository.save(dbCategory));
  }

  public ResponseEntity<String> deleteCategory(Long id, String userID) {
    Optional<Category> category = categoryRepository.findById(id);
    boolean allowDeleteUndefined = true;
    if (category.isPresent() && userID.equals(category.get().getUserID())) {
      category.get().getIdentifier()
          .forEach(identifier -> identifierService.deleteIdentifier(identifier.getId(),
              allowDeleteUndefined));
      categoryRepository.delete(category.get());
      return ResponseEntity.ok(String.join(" ", "Category with id:", id.toString(), "deleted"));
    }
    throw new CategoryNotFoundException("Category with id not found");
  }

}
