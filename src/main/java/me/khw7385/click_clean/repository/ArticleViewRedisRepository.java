package me.khw7385.click_clean.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ArticleViewRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public void saveView(Long userId, Long articleId){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        String key = "article:" + articleId + ":user:"+userId;
        operations.set(key, "viewed", 1, TimeUnit.MINUTES);
    }

    public boolean isViewed(Long userId, Long articleId){
        String key = "article:" + articleId + ":user:"+userId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void extendExpiration(Long userId, Long articleId){
        String key = "article:" + articleId + ":user:"+userId;
        redisTemplate.expire(key, Duration.ofMinutes(1));
    }

    public void incrementViewCount(Long articleId){
        String key = "article:views";
        redisTemplate.opsForZSet().incrementScore(key, articleId.toString(), 1);
    }

    public List<Long> findArticlesTop5(){
        String key = "article:views";
        Set<String> topArticles = redisTemplate.opsForZSet().reverseRange(key, 0, 4);

        if(topArticles == null || topArticles.isEmpty()){
            return Collections.emptyList();
        }
        return topArticles.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}
