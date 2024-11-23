package me.khw7385.click_clean.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.Scrap;
import me.khw7385.click_clean.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScrapRepository {
    private final EntityManager em;

    public void save(Scrap scrap){
        em.persist(scrap);
    }

    public void remove(Scrap scrap){em.remove(scrap);}

    public Optional<Scrap> findById(long id){
        Scrap scrap = em.find(Scrap.class, id);
        return Optional.ofNullable(scrap);
    }

    public Optional<Scrap> findByUserAndArticle(User user, Article article){
        return  em.createQuery("select s from Scrap s where s.user = :user and s.article = :article", Scrap.class)
                .setParameter("user", user)
                .setParameter("article", article)
                .getResultStream()
                .findFirst();
    }

    public List<Scrap> findScrapsByUser(User user, int page, int size){
        return em.createQuery("select s from Scrap s join fetch s.article a where s.user = :user", Scrap.class)
                .setParameter("user", user)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }
}
