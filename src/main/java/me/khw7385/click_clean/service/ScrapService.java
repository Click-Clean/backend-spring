package me.khw7385.click_clean.service;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.Scrap;
import me.khw7385.click_clean.domain.User;
import me.khw7385.click_clean.dto.ScrapRequest;
import me.khw7385.click_clean.exception.ClickCleanException;
import me.khw7385.click_clean.exception.error_code.ArticleErrorCode;
import me.khw7385.click_clean.exception.error_code.UserErrorCode;
import me.khw7385.click_clean.repository.ArticleRepository;
import me.khw7385.click_clean.repository.ScrapRepository;
import me.khw7385.click_clean.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void saveScrap(ScrapRequest scrapRequest){
        User user = userRepository.findById(scrapRequest.getUser_id()).orElseThrow(() -> new ClickCleanException(UserErrorCode.USER_NOT_FOUND_ERROR));
        Article article = articleRepository.findById(scrapRequest.getArticle_id()).orElseThrow(() -> new ClickCleanException(ArticleErrorCode.ARTICLE_NOT_FOUND_ERROR));

        scrapRepository.save(toEntity(scrapRequest, user, article));
    }

    private Scrap toEntity(ScrapRequest scrapRequest, User user, Article article){
        return Scrap.builder()
                .user(user)
                .article(article)
                .url(scrapRequest.getUrl())
                .build();
    }
}
