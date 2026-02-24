package com.example.vibeapp.post;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 12; i++) {
            postRepository.save(new Post(
                    (long) i,
                    "테스트 게시글 제목 " + i,
                    "테스트 게시글 내용입니다. " + i,
                    LocalDateTime.now().minusDays(10 - i),
                    LocalDateTime.now().minusDays(10 - i),
                    i * 10));
        }
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPagedPosts(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        // 게시글 번호 내림차순 정렬 (최신순)
        allPosts.sort((p1, p2) -> p2.getNo().compareTo(p1.getNo()));

        int start = (page - 1) * size;
        int end = Math.min(start + size, allPosts.size());

        if (start >= allPosts.size()) {
            return List.of();
        }

        return allPosts.subList(start, end);
    }

    public int getTotalCount() {
        return postRepository.findAll().size();
    }

    public Post getPostWithViewCount(Long no) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return post;
    }

    public Post getPost(Long no) {
        return postRepository.findByNo(no);
    }

    public void addPost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
    }

    public void updatePost(Long no, String title, String content) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
        }
    }

    public void deletePost(Long no) {
        postRepository.deleteByNo(no);
    }
}
