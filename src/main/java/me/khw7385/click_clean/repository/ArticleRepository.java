package me.khw7385.click_clean.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final EntityManager em;

    public Optional<Article> findById(long id){
        return Optional.of(em.find(Article.class, id));
    }

    public List<Article> findArticlesByIds(List<Long> ids){
        return em.createQuery("SELECT a FROM Article a WHERE a.id IN :ids", Article.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public List<Article> findArticlesByKeywordOrderByCreatedAtDesc(String keyword, int page, int size){
        return em.createQuery("SELECT a FROM Article a WHERE a.title LIKE CONCAT('%', :keyword, '%') OR a.body LIKE CONCAT('%', :keyword, '%') ORDER BY a.createdAt desc", Article.class)
                .setParameter("keyword", keyword)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }
}
