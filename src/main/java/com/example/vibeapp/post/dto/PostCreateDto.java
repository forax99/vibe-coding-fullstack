package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record PostCreateDto(
        @NotBlank(message = "제목은 필수입니다.") @Size(max = 100, message = "제목은 100자 이내여야 합니다.") String title,

        @NotBlank(message = "내용은 필수입니다.") String content,

        String tags) { // 쉼표로 구분된 태그 문자열 (예: "Spring, Java, H2")

    public PostCreateDto() {
        this("", "", "");
    }

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content);
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.setViews(0);
        return post;
    }
}
