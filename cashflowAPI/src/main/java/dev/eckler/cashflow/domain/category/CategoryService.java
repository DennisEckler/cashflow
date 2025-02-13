package dev.eckler.cashflow.domain.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.eckler.cashflow.domain.identifier.IdentifierService;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.CategoryResponse;
import dev.eckler.cashflow.openapi.model.CategoryUpdateRequest;

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

  public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
    Category category = CategoryMapper.categoryCreateRequestToCategory(categoryCreateRequest);
    Category response = cr.save(category);
    return CategoryMapper.categoryToCategoryResponse(response);
  }

  public CategoryResponse changeType(CategoryUpdateRequest categoryUpdateRequest, String userID) {
    Category category = cr.findByIdAndUserID(categoryUpdateRequest.getId(), userID)
        .orElseThrow(() -> new CategoryNotFoundException(userID));
    category.setType(categoryUpdateRequest.getType());
    CategoryResponse response = CategoryMapper.categoryToCategoryResponse(cr.save(category));
    return response;
  }

  public void deleteCategory(Long id, String userID) {
    System.out.println("xdxdxd");
    Optional<Category> category = cr.findByIdAndUserID(id, userID);
    boolean allowDeleteUndefined = true;
    if (category.isPresent()) {
      category.get().getIdentifier()
        // TODO: use cascade wtf?
          .forEach(identifier -> is.deleteIdentifier(identifier.getId(),
              allowDeleteUndefined));
      cr.delete(category.get());
    }
    throw new CategoryNotFoundException("Category with id not found");
  }

}
