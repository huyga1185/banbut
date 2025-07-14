package com.web.banbut.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_item_id")
    private String cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "pen_variant_id", nullable = false)
    private PenVariant penVariant;

    @Column(nullable = false)
    private int quantity;

    public CartItem() {}

    public CartItem(int quantity, PenVariant penVariant, String cartItemId, LocalDateTime createdAt, LocalDateTime updatedAt, Cart cart) {
        this.cartItemId = cartItemId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cart = cart;
        this.penVariant = penVariant;
        this.quantity = quantity;
    }

    public CartItem(Cart cart, PenVariant penVariant, int quantity) {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.cart = cart;
        this.penVariant = penVariant;
        this.quantity = quantity;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public PenVariant getPenVariant() {
        return penVariant;
    }

    public void setPenVariant(PenVariant penVariant) {
        this.penVariant = penVariant;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
