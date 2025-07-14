package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PenVariantCreationResponse {
    @JsonProperty("pen_variant_id")
    private String penVariantId;
    @JsonProperty("pen_id")
    private String penId;
    private String color;
    private int quantity;
    private double price;
    private String tip;

    public PenVariantCreationResponse(String penVariantId, String tip, double price, String color, int quantity, String penId) {
        this.penVariantId = penVariantId;
        this.tip = tip;
        this.price = price;
        this.color = color;
        this.quantity = quantity;
        this.penId = penId;
    }

    public String getPenVariantId() {
        return penVariantId;
    }

    public void setPenVariantId(String penVariantId) {
        this.penVariantId = penVariantId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
    }
}
