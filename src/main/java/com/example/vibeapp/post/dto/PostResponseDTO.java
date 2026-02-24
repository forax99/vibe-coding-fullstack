package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long no,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer views,
        List<String> tags) {
    public static PostResponseDto from(Post post) {
        return from(post, List.of());
    }

    public static PostResponseDto from(Post post, List<String> tags) {
        return new PostResponseDto(
                post.getNo(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getViews(),
                tags);
    }
}
