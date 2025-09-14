package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderResponse {
    @JsonProperty("pen_id")
    private String penId;
    @JsonProperty("address_id")
    private String addressID;
    private String note;
    private String status;
    private int quantity;
    private double total_price;

    public OrderResponse() {}

    public OrderResponse(String penId, String addressID, String note, String status, int quantity, double total_price) {
        this.penId = penId;
        this.addressID = addressID;
        this.note = note;
        this.status = status;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
