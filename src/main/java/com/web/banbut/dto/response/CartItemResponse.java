package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemResponse {
    @JsonProperty("cart_item_id")
    private String cartItemId;
    @JsonProperty("cart_id")
    private String cartId;
    private String name;
    private double price;
    private int quantity;
    @JsonProperty("total_price")
    private double totalPrice;
    @JsonProperty("image_url")
    private String imageUrl;

    public CartItemResponse(String cartItemId, String cartId, String name, double price, int quantity, double totalPrice, String imageUrl) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
