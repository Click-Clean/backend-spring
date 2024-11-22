package me.khw7385.click_clean.config;

import jakarta.persistence.EntityManager;
import me.khw7385.click_clean.repository.ArticleRepository;
import me.khw7385.click_clean.repository.ArticleRepositoryTest;
import me.khw7385.click_clean.repository.ScrapRepository;
import me.khw7385.click_clean.repository.VoteRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@TestConfiguration
public class TestConfig {
    @Bean
    public ScrapRepository scrapRepository(EntityManager entityManager){
        return new ScrapRepository(entityManager);
    }
    @Bean
    public VoteRepository voteRepository(EntityManager entityManager){return new VoteRepository(entityManager);}
    @Bean
    public ArticleRepository articleRepository(EntityManager entityManager){return new ArticleRepository(entityManager);}
}
