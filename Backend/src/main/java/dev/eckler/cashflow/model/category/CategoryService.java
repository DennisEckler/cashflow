package dev.eckler.cashflow.model.category;

import dev.eckler.cashflow.model.identifier.IdentifierService;
import java.util.List;
import java.util.Optional;
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

  public void saveCategories(List<Category> categories) {
    categoryRepository.saveAll(categories);
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
