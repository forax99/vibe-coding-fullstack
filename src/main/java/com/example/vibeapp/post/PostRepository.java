package com.example.vibeapp.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Spring Data JPA Repository 인터페이스
 * JpaRepository를 상속받으면 기본적인 CRUD 메서드가 자동으로 생성됩니다.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /** 
     * 모든 게시글을 PK(no) 내림차순으로 조회하는 쿼리 메서드
     * ORDER BY p.no DESC 와 동일한 동작을 수행합니다.
     */
    List<Post> findAllByOrderByNoDesc();

    /**
     * 페이징 처리를 포함한 게시글 목록 조회
     * Pageable을 통해 offset, size, sort를 유연하게 관리할 수 있습니다.
     */
    Page<Post> findAll(Pageable pageable);

    /**
     * 식별자(no)로 단건 조회
     * JpaRepository에서 기본 제공하는 findById를 사용할 수 있지만, 
     * 기존 코드와의 일치성을 위해 메서드를 정의하거나 그대로 사용합니다.
     */
    // Post findByNo(Long no); // findById로 대체 가능
}
