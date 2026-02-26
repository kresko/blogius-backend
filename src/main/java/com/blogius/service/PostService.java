package com.blogius.service;

import com.blogius.entity.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PostService {
    public List<Post> getAllPosts() {
        return Post.listAll();
    }

    public Post getPostById(Long id) {
        return Post.findById(id);
    }

    @Transactional
    public Post createPost(Post post) {
        post.createdAt = LocalDateTime.now();
        post.updated_at = LocalDateTime.now();
        post.persist();
        return post;
    }

    @Transactional
    public Post updatePost(Long id, Post updatedPost) {
        Post post = Post.findById(id);
        if (post != null) {
            post.title = updatedPost.title;
            post.content = updatedPost.content;
            post.author = updatedPost.author;
            post.updated_at = updatedPost.updated_at;
            post.persist();
        }
        return post;
    }

    @Transactional
    public boolean deletePost(Long id) {
        return Post.deleteById(id);
    }
}
