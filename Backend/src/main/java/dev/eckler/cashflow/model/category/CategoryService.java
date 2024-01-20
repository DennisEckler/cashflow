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
    for (Category category : categories) {
      if (category.getCategoryID() == null || hasNoneDefaultIdentifier(category.getCategoryID())) {
        setDefaultIdentifier(category);
      }
      categoryRepository.save(category);
    }
  }

  private void setDefaultIdentifier(Category category) {
    Set<Identifier> identifiers = category.getIdentifier();
    identifiers.add(new Identifier(DEFAULT_IDENTIFIER, category));
    category.setIdentifier(identifiers);
  }

  private boolean hasNoneDefaultIdentifier(Long categoryID) {
    return categoryRepository.getReferenceById(categoryID).getIdentifier().stream()
        .noneMatch(identifier -> identifier.getIdentifierLabel().equals(DEFAULT_IDENTIFIER));
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
