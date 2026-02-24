package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostListDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /** 페이징된 게시글 목록 조회 (Spring Data JPA PageRequest 사용) */
    @Transactional(readOnly = true)
    public List<PostListDto> findPagedPosts(int page, int size) {
        // PageRequest는 0-indexed 이므로 page - 1 처리
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "no"));
        return postRepository.findAll(pageRequest).getContent().stream()
                .map(PostListDto::from)
                .toList();
    }

    /** 전체 게시글 수 (Spring Data JPA count() 사용) */
    @Transactional(readOnly = true)
    public int countPosts() {
        return (int) postRepository.count();
    }

    /** 조회수 증가 후 단건 반환 */
    @Transactional
    public PostResponseDto viewPost(Long no) {
        // findById()는 Optional을 반환합니다.
        Post post = postRepository.findById(no).orElse(null);
        if (post == null)
            return null;
        post.setViews(post.getViews() + 1);
        post.setUpdatedAt(LocalDateTime.now());
        // save() 메서드는 내부적으로 persist() 또는 merge()를 호출합니다.
        postRepository.save(post);
        List<String> tags = postTagRepository.findByPostNo(no).stream().map(PostTag::getTagName).toList();
        return PostResponseDto.from(post, tags);
    }

    /** 수정용 단건 조회 (조회수 불변) */
    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long no) {
        Post post = postRepository.findById(no).orElse(null);
        if (post == null)
            return null;
        List<String> tags = postTagRepository.findByPostNo(no).stream().map(PostTag::getTagName).toList();
        return PostResponseDto.from(post, tags);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addPost(PostCreateDto dto) {
        // Save post entity
        var post = dto.toEntity();
        // save() 후 영속화된 엔티티의 ID(no)가 자동으로 채워집니다.
        postRepository.save(post);
        
        // Parse tags and insert
        if (dto.tags() != null && !dto.tags().isBlank()) {
            List<String> tagList = List.of(dto.tags().split(","))
                    .stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            for (String tag : tagList) {
                PostTag postTag = new PostTag();
                postTag.setPostNo(post.getNo());
                postTag.setTagName(tag);
                postTagRepository.save(postTag);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePost(Long no, PostUpdateDto dto) {
        var post = postRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        dto.updateEntity(post);
        // 트랜잭션 내에서 영속 상태 엔티티의 값만 바꿔도 Dirty Checking으로 업데이트되지만, 명시적으로 save() 호출 가능
        postRepository.save(post);
        
        // Update tags: delete existing then insert new
        postTagRepository.deleteAllByPostNo(no);
        if (dto.tags() != null && !dto.tags().isBlank()) {
            List<String> tagList = List.of(dto.tags().split(","))
                    .stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            for (String tag : tagList) {
                PostTag postTag = new PostTag();
                postTag.setPostNo(no);
                postTag.setTagName(tag);
                postTagRepository.save(postTag);
            }
        }
    }

    /** 게시글 삭제 */
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long no) {
        // deleteById()를 사용하여 간결하게 처리
        postRepository.deleteById(no);
    }
}
