package dev.eckler.cashflow.model.category;

import dev.eckler.cashflow.model.identifier.Identifier;
import dev.eckler.cashflow.model.identifier.IdentifierService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final IdentifierService identifierService;
  private static final String DEFAULT_IDENTIFIER = "undefined";


  public CategoryService(CategoryRepository categoryRepository,
      IdentifierService identifierService) {
    this.categoryRepository = categoryRepository;
    this.identifierService = identifierService;
  }

  public List<Category> getCategoriesByUser(String userID) {
    return categoryRepository.findAllByUserID(userID);
  }

  public void saveCategories(List<Category> categories) {
    categoryRepository.saveAll(categories);
    categories.forEach(category -> {
      Set<Identifier> identifiers = category.getIdentifier();
      Optional<Identifier> identifier = identifiers.stream()
          .filter(identifierFilter -> identifierFilter.getIdentifierLabel().equals(DEFAULT_IDENTIFIER))
          .findFirst();
      if (identifier.isEmpty()){
        identifierService.saveIdentifier(category, DEFAULT_IDENTIFIER);
      }
    });
  }

  public boolean deleteCategory(Long categoryID) {
    Optional<Category> category = categoryRepository.findById(categoryID);
    if (category.isPresent()) {
      category.get().getIdentifier()
          .forEach(identifier -> identifierService.deleteIdentifier(identifier.getIdentifierID()));
      categoryRepository.delete(category.get());
      return true;
    }
    return false;
  }

}
