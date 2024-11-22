package me.khw7385.click_clean.repository;

import me.khw7385.click_clean.config.RedisConfig;
import me.khw7385.click_clean.config.TestConfig;
import me.khw7385.click_clean.config.TestRedisConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;


@DataJpaTest
@Import(TestRedisConfig.class)
public class ArticleViewRedisRepositoryTest {
    @Autowired
    private ArticleViewRedisRepository articleViewRedisRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void saveView(){
        // given
        String key = "article:1:user:1";
        // when
        articleViewRedisRepository.saveView(1L, 1L);
        // then
        Assertions.assertThat(redisTemplate.opsForValue().get(key)).isEqualTo("viewed");
    }

    @Test
    void isViewed(){
        // given
        articleViewRedisRepository.saveView(1L, 1L);

        // when
        boolean hasView = articleViewRedisRepository.isViewed(1L, 1L);

        // then
        Assertions.assertThat(hasView).isTrue();
    }

    @Test
    void countViews(){
        // given
        for (int i = 0; i < 10; i++) {
            for (int j = i; j < 30; j++) {
                articleViewRedisRepository.incrementViewCount((long) i);
            }
        }

        // when
        List<Long> articles = articleViewRedisRepository.findArticlesTop5();

        // then
        Assertions.assertThat(articles).containsExactly(0L, 1L, 2L, 3L, 4L);
    }
}
