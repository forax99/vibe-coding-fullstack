package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostTagRepository {

    /** 특정 게시글의 태그 목록 조회 */
    List<PostTag> findByPostNo(@Param("postNo") Long postNo);

    /** 태그 추가 (auto-increment id) */
    void insert(PostTag postTag);

    /** 특정 게시글의 특정 태그 삭제 */
    void deleteByPostNoAndTagName(@Param("postNo") Long postNo, @Param("tagName") String tagName);

    /** 특정 게시글의 태그 전체 삭제 */
    void deleteAllByPostNo(@Param("postNo") Long postNo);
}
