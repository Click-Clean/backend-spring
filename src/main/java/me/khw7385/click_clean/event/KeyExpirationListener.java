package me.khw7385.click_clean.event;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.repository.ArticleViewRedisRepository;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component
@RequiredArgsConstructor
public class KeyExpirationListener implements MessageListener {
    private final ArticleViewRedisRepository articleViewRedisRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        long articleId = Long.parseLong(message.toString().split(":")[1]);
        articleViewRedisRepository.decrementViewCount(articleId);
    }
}
