package com.web.banbut.dto.response;

import java.util.List;

public class CategoryListResponse {
    List<CategoryResponse> categoryResponses;

    public CategoryListResponse(List<CategoryResponse> categoryResponses) {
        this.categoryResponses = categoryResponses;
    }

    public List<CategoryResponse> getCategoryResponses() {
        return categoryResponses;
    }

    public void setCategoryResponses(List<CategoryResponse> categoryResponses) {
        this.categoryResponses = categoryResponses;
    }
}
