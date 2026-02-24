package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

public class PostResponseDTO {
    private Long no;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer views;

    public PostResponseDTO() {
    }

    public static PostResponseDTO from(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.no = post.getNo();
        dto.title = post.getTitle();
        dto.content = post.getContent();
        dto.createdAt = post.getCreatedAt();
        dto.updatedAt = post.getUpdatedAt();
        dto.views = post.getViews();
        return dto;
    }

    public Long getNo() {
        return no;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Integer getViews() {
        return views;
    }
}
