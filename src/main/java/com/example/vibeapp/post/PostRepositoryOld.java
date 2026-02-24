package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryOld {

    @PersistenceContext
    private EntityManager em;

    /** JPQL을 사용하여 모든 게시글을 최신순으로 조회 */
    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p ORDER BY p.no DESC", Post.class)
                .getResultList();
    }

    /** JPQL과 setFirstResult/setMaxResults를 이용한 페이징 처리 */
    public List<Post> findPaged(int offset, int size) {
        return em.createQuery("SELECT p FROM Post p ORDER BY p.no DESC", Post.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    /** 전체 데이터 개수 조회 */
    public int count() {
        return ((Number) em.createQuery("SELECT COUNT(p) FROM Post p").getSingleResult()).intValue();
    }

    /** 식별자(PK)로 단건 조회 */
    public Post findByNo(Long no) {
        return em.find(Post.class, no);
    }

    /** persist()를 이용한 새로운 엔티티 저장 */
    public void insert(Post post) {
        em.persist(post);
    }

    /** 
     * 변경 감지(Dirty Checking) 또는 merge()를 통한 수정 
     * 영속성 컨텍스트가 관리하는 엔티티의 상태가 변경되면 트랜잭션 커밋 시점에 자동으로 DB에 반영됩니다.
     */
    public void update(Post post) {
        em.merge(post);
    }

    /** 엔티티를 찾아 영속성 컨텍스트에서 제거(삭제) */
    public void deleteByNo(Long no) {
        Post post = em.find(Post.class, no);
        if (post != null) {
            em.remove(post);
        }
    }
}
