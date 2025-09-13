package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemRequest {
    @JsonProperty("pen_variant_id")
    private String penVariantID;
    private int quantity;

    public CartItemRequest() {}
    public CartItemRequest(
    String penVariantID,
    int quantity
    ) {
        this.penVariantID = penVariantID;
        this.quantity = quantity;
    }

    public String getPenVariantID() {
        return penVariantID;
    }
    
    public void setPenVariantID(String penVariantID) {
        this.penVariantID = penVariantID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
