package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ImageResponse {
    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("image_detail")
    private FileResponse imageDetail;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public ImageResponse(String imageId, FileResponse imageDetail, LocalDateTime createdAt) {
        this.imageId = imageId;
        this.imageDetail = imageDetail;
        this.createdAt = createdAt;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public FileResponse getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(FileResponse imageDetail) {
        this.imageDetail = imageDetail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
