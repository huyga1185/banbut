package com.web.banbut.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id")
    private String imageId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "pen_id", nullable = false)
    private Pen pen;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Image() {
    }

    public Image(String name, Pen pen) {
        this.name = name;
        this.pen = pen;
        this.createdAt = LocalDateTime.now();
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
