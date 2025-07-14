package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryCreationResponse {
    @JsonProperty("category_id")
    private String categoryId;
    private String name;

    public CategoryCreationResponse(String categoryId, String name) {
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
