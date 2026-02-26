package com.blogius.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Post extends PanacheEntity {

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
