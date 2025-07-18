package com.web.banbut.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class PenVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("pen_variant_id")
    private String penVariantId;

    private String color;

    private int quantity;

    private double price;

    private String tip;

    private boolean visible;

    @ManyToOne
    @JoinColumn(name = "pen_id", nullable = false)
    private Pen pen;

    @OneToMany(mappedBy = "penVariant")
    private Set<CartItem> cartItems;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PenVariant() {
    }

    public PenVariant(String color, int quantity, double price, String tip, Pen pen) {
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.tip = tip;
        this.pen = pen;
        this.visible = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public PenVariant(String penVariantId, String color, int quantity, double price, String tip, Pen pen, LocalDateTime createdAt, LocalDateTime updatedAt, boolean visible) {
        this.penVariantId = penVariantId;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.tip = tip;
        this.pen = pen;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getPenVariantId() {
        return penVariantId;
    }

    public void setPenVariantId(String penVariantId) {
        this.penVariantId = penVariantId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Pen getPen() {
        return pen;
    }

    public void setPen(Pen pen) {
        this.pen = pen;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
