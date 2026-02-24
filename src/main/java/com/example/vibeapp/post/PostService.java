package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostListDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
    }

    /** 페이징된 게시글 목록 조회 (DB에서 직접 페이징) */
    public List<PostListDto> findPagedPosts(int page, int size) {
        int offset = (page - 1) * size;
        return postRepository.findPaged(offset, size).stream()
                .map(PostListDto::from)
                .toList();
    }

    /** 전체 게시글 수 */
    public int countPosts() {
        return postRepository.count();
    }

    /** 조회수 증가 후 단건 반환 */
    public PostResponseDto viewPost(Long no) {
        Post post = postRepository.findByNo(no);
        if (post == null)
            return null;
        post.setViews(post.getViews() + 1);
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);
        List<String> tags = postTagRepository.findByPostNo(no).stream().map(PostTag::getTagName).toList();
        return PostResponseDto.from(post, tags);
    }

    /** 수정용 단건 조회 (조회수 불변) */
    public PostResponseDto findPost(Long no) {
        Post post = postRepository.findByNo(no);
        if (post == null)
            return null;
        List<String> tags = postTagRepository.findByPostNo(no).stream().map(PostTag::getTagName).toList();
        return PostResponseDto.from(post, tags);
    }

    @Transactional
    public void addPost(PostCreateDto dto) {
        // Save post entity
        var post = dto.toEntity();
        postRepository.insert(post);
        // After insert, post.no is set (assuming MyBatis generated key)
        // Parse tags and insert
        if (dto.tags() != null && !dto.tags().isBlank()) {
            List<String> tagList = List.of(dto.tags().split(","))
                    .stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            for (String tag : tagList) {
                PostTag postTag = new PostTag();
                postTag.setPostNo(post.getNo());
                postTag.setTagName(tag);
                postTagRepository.insert(postTag);
            }
        }
    }

    @Transactional
    public void updatePost(Long no, PostUpdateDto dto) {
        var post = postRepository.findByNo(no);
        if (post == null) {
            throw new IllegalArgumentException("Post not found");
        }
        dto.updateEntity(post);
        postRepository.update(post);
        // Update tags: delete existing then insert new
        postTagRepository.deleteAllByPostNo(no);
        if (dto.tags() != null && !dto.tags().isBlank()) {
            List<String> tagList = List.of(dto.tags().split(","))
                    .stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            for (String tag : tagList) {
                PostTag postTag = new PostTag();
                postTag.setPostNo(no);
                postTag.setTagName(tag);
                postTagRepository.insert(postTag);
            }
        }
    }

    /** 게시글 삭제 */
    public void deletePost(Long no) {
        postRepository.deleteByNo(no);
    }
}
