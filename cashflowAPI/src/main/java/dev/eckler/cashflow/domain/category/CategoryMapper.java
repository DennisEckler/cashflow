package dev.eckler.cashflow.domain.category;

import dev.eckler.cashflow.domain.identifier.IdentifierMapper;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.CategoryResponse;

public class CategoryMapper{

  public static CategoryResponse categoryToCategoryResponse(Category category){
    CategoryResponse categoryResponse = new CategoryResponse();
    if (category != null){
      categoryResponse.setId(category.getId());
      categoryResponse.setUserID(category.getUserID());
      categoryResponse.setLabel(category.getLabel());
      categoryResponse.setIdentifier(IdentifierMapper.identifierSetToList(category.getIdentifier()));
      categoryResponse.setType(category.getType());
    }
    return categoryResponse;
  }

  public static Category categoryCreateRequestToCategory(CategoryCreateRequest request){
    if (request != null){
      return new Category(request.getLabel(), request.getUserID());
    }
    throw new IllegalArgumentException("CategoryCreateRequest can not be null");
  }
}
