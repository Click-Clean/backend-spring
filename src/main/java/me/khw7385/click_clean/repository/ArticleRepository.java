package me.khw7385.click_clean.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final EntityManager em;

    public Optional<Article> findById(long id){
        return Optional.of(em.find(Article.class, id));
    }
}
