package dev.eckler.cashflow.model.category;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository){
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getAllCategories(String userID){
//    return categoryRepository.findAllByUserID(userID);
    return categoryRepository.findAllCategories();
  }



}
