package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PenResponse {
    @JsonProperty("pen_id")
    private String penId;
    private String name;
    private boolean visible;
    @JsonProperty("brand_id")
    private String brandId;
    @JsonProperty("category_id")
    private String categoryId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    @JsonProperty("image_url")
    private String imageUrl;

    public PenResponse(
            String penId,
            String name,
            boolean visible,
            String brandId,
            String categoryId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String imageUrl
    ) {
        this.penId = penId;
        this.name = name;
        this.visible = visible;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
