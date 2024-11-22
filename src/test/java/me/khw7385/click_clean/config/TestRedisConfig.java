package me.khw7385.click_clean.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import me.khw7385.click_clean.repository.ArticleViewRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    private int port = 6380;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer(){
         redisServer = RedisServer.builder()
                .port(port)
                .setting("maxmemory 24M")
                .build();

        redisServer.start();
    }

    @PreDestroy
    public void stopRedis(){
        if(redisServer != null){
            redisServer.stop();
        }
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public ArticleViewRedisRepository articleViewRedisRepository(){
        return new ArticleViewRedisRepository(redisTemplate());
    }
}