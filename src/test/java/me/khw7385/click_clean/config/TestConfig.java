package me.khw7385.click_clean.config;

import jakarta.persistence.EntityManager;
import me.khw7385.click_clean.repository.ScrapRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public ScrapRepository scrapRepository(EntityManager entityManager){
        return new ScrapRepository(entityManager);
    }
}