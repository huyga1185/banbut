package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PenCreationRequest {
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

    public PenCreationRequest(String name, String tip, int quantity, String color, String brandId, String categoryId, double price, boolean visible) {
        this.name = name;
        this.tip = tip;
        this.quantity = quantity;
        this.color = color;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.price = price;
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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
}
