package com.blogius.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Post extends PanacheEntity {

    @NotBlank
    @Column(nullable = false)
    public String title;

    @Column(columnDefinition = "TEXT")
    public String content;

    public String author;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updated_at;

    public Post() {
        this.createdAt = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }
}
