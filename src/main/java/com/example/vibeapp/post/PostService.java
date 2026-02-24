package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<PostListDto> findPagedPosts(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        // 게시글 번호 내림차순 정렬 (최신순)
        allPosts.sort((p1, p2) -> p2.getNo().compareTo(p1.getNo()));

        int start = (page - 1) * size;
        int end = Math.min(start + size, allPosts.size());

        if (start >= allPosts.size()) {
            return List.of();
        }

        return allPosts.subList(start, end).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int countPosts() {
        return postRepository.findAll().size();
    }

    public PostResponseDTO viewPost(Long no) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            post.setViews(post.getViews() + 1);
            return PostResponseDTO.from(post);
        }
        return null;
    }

    public PostResponseDTO findPost(Long no) {
        Post post = postRepository.findByNo(no);
        return post != null ? PostResponseDTO.from(post) : null;
    }

    public void addPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        postRepository.save(post);
    }

    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            updateDto.updateEntity(post);
        }
    }

    public void deletePost(Long no) {
        postRepository.deleteByNo(no);
    }
}
