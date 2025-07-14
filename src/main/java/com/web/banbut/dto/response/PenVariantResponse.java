package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PenVariantResponse {
    @JsonProperty("pen_variant_id")
    private String penVariantId;
    @JsonProperty("pen_id")
    private String penId;
    private String color;
    private int quantity;
    private double price;
    private String tip;
    private boolean visible;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public PenVariantResponse(String penVariantId, String penId, String color, int quantity, double price, String tip, boolean visible, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.penVariantId = penVariantId;
        this.penId = penId;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.tip = tip;
        this.visible = visible;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
    }

    public String getPenVariantId() {
        return penVariantId;
    }

    public void setPenVariantId(String penVariantId) {
        this.penVariantId = penVariantId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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
}
