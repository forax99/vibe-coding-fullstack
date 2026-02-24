package com.example.vibeapp.post;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Spring Data JPA Repository 인터페이스
 * JpaRepository를 상속받아 복잡한 구현 없이 데이터 접근 계층을 구현합니다.
 */
public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    /** 
     * 특정 게시글 번호(postNo)에 해당하는 모든 태그를 조회하는 쿼리 메서드
     * SELECT t FROM PostTag t WHERE t.postNo = :postNo 와 동일합니다.
     */
    List<PostTag> findByPostNo(Long postNo);

    /**
     * 특정 게시글 번호(postNo)와 태그 이름(tagName)이 일치하는 태그를 삭제하는 쿼리 메서드
     * JPQL: DELETE FROM PostTag t WHERE t.postNo = :postNo AND t.tagName = :tagName
     */
    void deleteByPostNoAndTagName(Long postNo, String tagName);

    /**
     * 특정 게시글 번호(postNo)에 해당하는 모든 태그를 삭제하는 쿼리 메서드
     * JPQL: DELETE FROM PostTag t WHERE t.postNo = :postNo
     */
    void deleteAllByPostNo(Long postNo);
}
