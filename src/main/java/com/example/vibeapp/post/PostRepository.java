package com.example.vibeapp.post;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public void save(Post post) {
        if (post.getNo() == null) {
            long nextNo = posts.stream()
                    .mapToLong(Post::getNo)
                    .max()
                    .orElse(0L) + 1;
            post.setNo(nextNo);
        }
        posts.add(post);
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public Post findByNo(Long no) {
        return posts.stream()
                .filter(post -> post.getNo().equals(no))
                .findFirst()
                .orElse(null);
    }

    public void deleteByNo(Long no) {
        posts.removeIf(post -> post.getNo().equals(no));
    }
}
