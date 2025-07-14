package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PenUpdateRequest {
    @JsonProperty("pen_id")
    private String penId;
    private String name;
    private Boolean visible;
    @JsonProperty("brand_id")
    private String brandId;
    @JsonProperty("category_id")
    private String categoryId;

    public PenUpdateRequest(
            String penId,
            String name,
            Boolean visible,
            String brandId,
            String categoryId
    ) {
        this.penId = penId;
        this.name = name;
        this.visible = visible;
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
