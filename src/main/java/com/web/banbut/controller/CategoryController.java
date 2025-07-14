package com.web.banbut.controller;

import com.web.banbut.dto.request.CategoryCreationRequest;
import com.web.banbut.dto.request.CategoryUpdateRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.dto.response.CategoryCreationResponse;
import com.web.banbut.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add-category")
    public ApiResponse<Map<String, Object>> createCategory(@RequestBody CategoryCreationRequest categoryCreationRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "category", categoryService.createCategory(categoryCreationRequest)
                )
        );
    }

    @PutMapping("/update")
    public ApiResponse<Map<String, Object>> updateCategory(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "category", categoryService.updateCategory(categoryUpdateRequest)
                )
        );
    }

    @GetMapping("get-list")
    public ApiResponse<Map<String, Object>> getListCategories() {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "category", categoryService.getListCategories()
                )
        );
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<Map<String, Object>> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ApiResponse<>(
                "success",
                Map.of(
                        "message", "Category deleted"
                )
        );
    }
}
