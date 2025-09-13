package com.web.banbut.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "brand_id")
    private String brandId;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "brand")
    Set<Pen> pens;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Brand() {
    }

    public Brand(String brandId, String name, Set<Pen> pens, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.brandId = brandId;
        this.name = name;
        this.pens = pens;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Brand(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pen> getPens() {
        return pens;
    }

    public void setPens(Set<Pen> pens) {
        this.pens = pens;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
