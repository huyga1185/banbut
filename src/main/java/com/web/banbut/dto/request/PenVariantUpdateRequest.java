package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PenVariantUpdateRequest {
    private String color;
    private Integer quantity;
    private Double price;
    private String tip;
    private Boolean visible;

    public PenVariantUpdateRequest(String color, Integer quantity, Double price, String tip, Boolean visible) {
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.tip = tip;
        this.visible = visible;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
