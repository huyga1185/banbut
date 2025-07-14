package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PenCreationResponse {
    @JsonProperty("pen_id")
    private String penId;
    private String name;
    private double price;
    private boolean visible;
    @JsonProperty("brand_id")
    private String brandId;
    @JsonProperty("category_id")
    private String categoryId;
    private String color;
    private int quantity;
    private String tip;

    public PenCreationResponse() {}

    public PenCreationResponse(String penId, String tip, int quantity, String categoryId, String color, String brandId, boolean visible, double price, String name) {
        this.penId = penId;
        this.tip = tip;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.color = color;
        this.brandId = brandId;
        this.visible = visible;
        this.price = price;
        this.name = name;
    }

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
