package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostTagRepository {

    /** 태그 추가 (auto-increment id) */
    void insert(PostTag postTag);

    /** 특정 게시글의 특정 태그 삭제 */
    void deleteByPostNoAndTagName(@Param("postNo") Long postNo, @Param("tagName") String tagName);

    /** 특정 게시글의 태그 전체 삭제 */
    void deleteAllByPostNo(@Param("postNo") Long postNo);
}
