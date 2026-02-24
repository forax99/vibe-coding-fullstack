package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostTagRepository {

    @PersistenceContext
    private EntityManager em;

    /** 특정 게시글에 속한 태그들을 JPQL로 조회 */
    public List<PostTag> findByPostNo(Long postNo) {
        return em.createQuery("SELECT t FROM PostTag t WHERE t.postNo = :postNo", PostTag.class)
                .setParameter("postNo", postNo)
                .getResultList();
    }

    /** persist()를 이용한 태그 엔티티 영속화 */
    public void insert(PostTag postTag) {
        em.persist(postTag);
    }

    /** 특정 게시글의 특정 태그를 JPQL로 찾아 삭제 */
    public void deleteByPostNoAndTagName(Long postNo, String tagName) {
        List<PostTag> tags = em.createQuery("SELECT t FROM PostTag t WHERE t.postNo = :postNo AND t.tagName = :tagName", PostTag.class)
                .setParameter("postNo", postNo)
                .setParameter("tagName", tagName)
                .getResultList();
        for (PostTag tag : tags) {
            em.remove(tag);
        }
    }

    /** 특정 게시글의 모든 태그를 JPQL로 한꺼번에 삭제 */
    public void deleteAllByPostNo(Long postNo) {
        em.createQuery("DELETE FROM PostTag t WHERE t.postNo = :postNo")
                .setParameter("postNo", postNo)
                .executeUpdate();
    }
}
