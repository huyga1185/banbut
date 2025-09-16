package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
    @JsonProperty("pen_variant_id")
    private String penVariantId;
    @JsonProperty("address_id")
    private String addressID;
    private String note;
    private int quantity;

    public OrderRequest() {}

    public OrderRequest(
            String penVariantId,
            String addressID,
            String note,
            int quantity
    ) {
        this.penVariantId = penVariantId;
        this.addressID = addressID;
        this.note = note;
        this.quantity = quantity;
    }

    public void setPenVariantId(String penVariantId) {
        this.penVariantId = penVariantId;
    }

    public String getPenVariantId() {
        return penVariantId;
    }

    public void setPenId(String penVariantId) {
        this.penVariantId = penVariantId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
