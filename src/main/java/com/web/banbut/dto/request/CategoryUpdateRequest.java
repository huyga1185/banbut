package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryUpdateRequest {
    @JsonProperty("brand_id")
    private String categoryId;
    private String name;

    public CategoryUpdateRequest(String categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
