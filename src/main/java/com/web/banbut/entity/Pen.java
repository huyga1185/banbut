package com.web.banbut.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Pen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pen_id")
    private String penId;

    private String name;

    private boolean visible;

    @OneToMany(mappedBy = "pen")
    private Set<PenVariant> penVariants;

    @OneToMany(mappedBy = "pen", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @OneToMany(mappedBy = "pen")
    private Set<Order> orders;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Pen() {
    }

    public Pen(String name, Brand brand, Category category) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.visible = true;
    }

    public Pen(String penId, LocalDateTime updatedAt, Category category, LocalDateTime createdAt, Brand brand, Set<Order> orders, Set<Image> images, Set<PenVariant> penVariants, String name, boolean visible) {
        this.penId = penId;
        this.updatedAt = updatedAt;
        this.category = category;
        this.createdAt = createdAt;
        this.brand = brand;
        this.orders = orders;
        this.images = images;
        this.penVariants = penVariants;
        this.name = name;
        this.visible = visible;
    }

    public String getPenId() {
        return penId;
    }

    public void setPenId(String penId) {
        this.penId = penId;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<PenVariant> getPenVariants() {
        return penVariants;
    }

    public void setPenVariants(Set<PenVariant> penVariants) {
        this.penVariants = penVariants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
