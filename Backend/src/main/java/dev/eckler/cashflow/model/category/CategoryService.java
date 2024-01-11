package dev.eckler.cashflow.model.category;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getAllCategoriesByUserID(String userID) {
    return categoryRepository.findAllByUserID(userID);
  }

}
