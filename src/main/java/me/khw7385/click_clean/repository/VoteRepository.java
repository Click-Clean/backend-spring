package me.khw7385.click_clean.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.Vote;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoteRepository {
    private final EntityManager em;

    public void save(Vote vote){em.persist(vote);}

    public void remove(Vote vote){em.remove(vote);}

    public int countTrueVotes(Article article){
        return ((Long)em.createQuery("SELECT COUNT(v) FROM Vote v WHERE v.article = :article AND v.voteValue = true")
                .setParameter("article", article)
                .getSingleResult()).intValue();
    }

    public int countFalseVotes(Article article){
        return ((Long)em.createQuery("SELECT COUNT(v) FROM Vote v WHERE v.article = :article AND v.voteValue = false")
                .setParameter("article", article)
                .getSingleResult()).intValue();
    }
}
