package com.web.banbut.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id")
    private String cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Cart() {}

    public Cart(String cartId, LocalDateTime createdAt, User user, Set<CartItem> cartItems) {
        this.cartId = cartId;
        this.createdAt = createdAt;
        this.user = user;
        this.cartItems = cartItems;
    }

    public Cart(User user) {
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
