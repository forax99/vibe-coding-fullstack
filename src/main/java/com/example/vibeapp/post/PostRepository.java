package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostRepository {

    /** 전체 목록 (최신순) */
    List<Post> findAll();

    /** 페이징 목록 */
    List<Post> findPaged(@Param("offset") int offset, @Param("size") int size);

    /** 총 게시글 수 */
    int count();

    /** 단건 조회 */
    Post findByNo(Long no);

    /** 새 게시글 삽입 (auto-increment no) */
    void insert(Post post);

    /** 게시글 수정 */
    void update(Post post);

    /** 게시글 삭제 */
    void deleteByNo(Long no);
}
