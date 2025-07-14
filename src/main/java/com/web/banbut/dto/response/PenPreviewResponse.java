package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PenPreviewResponse {
    @JsonProperty("pen_id")
    private String penId;
    private String name;
    private double price;
    @JsonProperty("image_url")
    private String imageUrl;

    public PenPreviewResponse(String penId, String name, double price, String imageUrl) {
        this.penId = penId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
