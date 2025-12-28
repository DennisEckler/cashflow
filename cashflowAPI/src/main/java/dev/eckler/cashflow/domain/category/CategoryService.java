package dev.eckler.cashflow.domain.category;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.CategoryResponse;
import dev.eckler.cashflow.openapi.model.CategoryUpdateRequest;

@Service
public class CategoryService {

    private final CategoryRepository cr;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository cr) {
        this.cr = cr;
    }

    public List<CategoryResponse> getCategoriesByUser(String userID) {
        List<CategoryResponse> categoryResponses = cr.findAllByUserID(userID).stream()
                .map(CategoryMapper::categoryToCategoryResponse)
                .toList();
        if (categoryResponses.isEmpty()) {
            throw new CategoryNotFoundException(userID);
        }
        return categoryResponses;
    }

    public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = CategoryMapper.categoryCreateRequestToCategory(categoryCreateRequest);
        logger.debug(category.toString());

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
        cr.findByIdAndUserID(id, userID)
                .ifPresentOrElse(
                        c -> cr.delete(c),
                        () -> {
                            throw new CategoryNotFoundException("Category with id not found");
                        });
    }

}
