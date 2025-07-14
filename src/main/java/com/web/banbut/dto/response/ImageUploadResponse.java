package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ImageUploadResponse {

    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("image_detail")
    private FileResponse imageDetail;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("pen_id")
    private String penId;

    public ImageUploadResponse(String imageId, FileResponse imageDetail, LocalDateTime createdAt, String penId) {
        this.imageId = imageId;
        this.imageDetail = imageDetail;
        this.createdAt = createdAt;
        this.penId = penId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FileResponse getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(FileResponse imageDetail) {
        this.imageDetail = imageDetail;
    }
}
