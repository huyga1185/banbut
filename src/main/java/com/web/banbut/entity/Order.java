package com.web.banbut.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private String orderId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, name = "total_price")
    private double totalPrice;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "pen_id", nullable = false)
    private Pen pen;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Order() {}

    public Order(String orderId, User user, Pen pen, String status, String address, double totalPrice, int quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.user = user;
        this.pen = pen;
        this.status = status;
        this.address = address;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(User user, Pen pen, String status, String address, int quantity, double totalPrice) {
        this.user = user;
        this.pen = pen;
        this.status = status;
        this.address = address;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setPen(Pen pen) {
        this.pen = pen;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public Pen getPen() {
        return pen;
    }

    public User getUser() {
        return user;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
