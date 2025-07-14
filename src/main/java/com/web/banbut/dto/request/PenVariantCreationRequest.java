package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PenVariantCreationRequest {
    @JsonProperty("pen_id")
    private String penId;
    private String color;
    private int quantity;
    private double price;
    private String tip;

    public String getPenId() {
        return penId;
    }

    public PenVariantCreationRequest(String penId, String tip, double price, String color, int quantity) {
        this.penId = penId;
        this.tip = tip;
        this.price = price;
        this.color = color;
        this.quantity = quantity;
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
}
