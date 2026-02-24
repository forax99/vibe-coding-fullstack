package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
    public PostResponseDTO viewPost(Long no) {
        Post post = postRepository.findByNo(no);
        if (post == null)
            return null;
        post.setViews(post.getViews() + 1);
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);
        return PostResponseDTO.from(post);
    }

    /** 수정용 단건 조회 (조회수 불변) */
    public PostResponseDTO findPost(Long no) {
        Post post = postRepository.findByNo(no);
        return post != null ? PostResponseDTO.from(post) : null;
    }

    /** 게시글 저장 */
    public void addPost(PostCreateDto createDto) {
        postRepository.insert(createDto.toEntity());
    }

    /** 게시글 수정 */
    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            updateDto.updateEntity(post);
            postRepository.update(post);
        }
    }

    /** 게시글 삭제 */
    public void deletePost(Long no) {
        postRepository.deleteByNo(no);
    }
}
