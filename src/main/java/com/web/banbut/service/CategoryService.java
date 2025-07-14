package com.web.banbut.service;

import com.web.banbut.dto.request.CategoryCreationRequest;
import com.web.banbut.dto.request.CategoryUpdateRequest;
import com.web.banbut.dto.response.CategoryCreationResponse;
import com.web.banbut.dto.response.CategoryListResponse;
import com.web.banbut.dto.response.CategoryResponse;
import com.web.banbut.dto.response.CategoryUpdateResponse;
import com.web.banbut.entity.Category;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.CategoryRepository;
import com.web.banbut.repository.PenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PenRepository penRepository;

    public CategoryService(CategoryRepository categoryRepository, PenRepository penRepository) {
        this.categoryRepository = categoryRepository;
        this.penRepository = penRepository;
    }

    public CategoryCreationResponse createCategory(CategoryCreationRequest categoryCreationRequest) {
        if (categoryRepository.existsByName(categoryCreationRequest.getName()))
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        Category category = categoryRepository.save(new Category(categoryCreationRequest.getName()));
        return new CategoryCreationResponse(
                category.getCategoryId(),
                category.getName()
        );
    }

    public CategoryUpdateResponse updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
        if (categoryRepository.existsByName(categoryUpdateRequest.getName()))
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
        Category category = categoryRepository.findById(categoryUpdateRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setName(categoryUpdateRequest.getName());
        category.setUpdatedAt(LocalDateTime.now());
        category = categoryRepository.save(category);
        return new CategoryUpdateResponse(
                category.getCategoryId(),
                category.getUpdatedAt(),
                category.getCreatedAt(),
                category.getName()
        );
    }

    public CategoryListResponse getListCategories() {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            CategoryResponse categoryResponse = new CategoryResponse(
                    category.getCategoryId(),
                    category.getName(),
                    category.getCreatedAt(),
                    category.getUpdatedAt()
            );
            categoryResponses.add(categoryResponse);
        }
        return new CategoryListResponse(categoryResponses);
    }

    public void deleteCategory(String categoryId) {
        if (!categoryRepository.existsById(categoryId))
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        else if (penRepository.existsPenByCategory_CategoryId(categoryId))
            throw new AppException(ErrorCode.CATEGORY_HAS_PRODUCTS);
        categoryRepository.deleteById(categoryId);
    }
}
