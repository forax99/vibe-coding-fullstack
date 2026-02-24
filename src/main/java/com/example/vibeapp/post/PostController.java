package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시글 REST API 컨트롤러
 * 모든 응답은 JSON 형식으로 반환됩니다.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /** 게시글 목록 조회 (페이징 포함) */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listPosts(@RequestParam(defaultValue = "1") int page) {
        int size = 5;
        List<PostListDto> posts = postService.findPagedPosts(page, size);
        int totalCount = postService.countPosts();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok(response);
    }

    /** 게시글 상세 조회 (조회수 증가) */
    @GetMapping("/{no}")
    public ResponseEntity<PostResponseDto> viewPost(@PathVariable Long no) {
        PostResponseDto post = postService.viewPost(no);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    /** 게시글 등록 */
    @PostMapping
    public ResponseEntity<Void> addPost(@Valid @RequestBody PostCreateDto postCreateDto) {
        postService.addPost(postCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** 게시글 수정 */
    @PutMapping("/{no}")
    public ResponseEntity<Void> updatePost(@PathVariable Long no, @Valid @RequestBody PostUpdateDto postUpdateDto) {
        try {
            postService.updatePost(no, postUpdateDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** 게시글 삭제 */
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deletePost(@PathVariable Long no) {
        postService.deletePost(no);
        return ResponseEntity.noContent().build();
    }
}
