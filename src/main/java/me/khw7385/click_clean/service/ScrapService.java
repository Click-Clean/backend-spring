package me.khw7385.click_clean.service;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.Scrap;
import me.khw7385.click_clean.domain.User;
import me.khw7385.click_clean.dto.ScrapDto;
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
    public void saveScrap(ScrapDto.Command command){
        User user = userRepository.findById(command.userId()).orElseThrow(() -> new ClickCleanException(UserErrorCode.USER_NOT_FOUND_ERROR));
        Article article = articleRepository.findById(command.articleId()).orElseThrow(() -> new ClickCleanException(ArticleErrorCode.ARTICLE_NOT_FOUND_ERROR));

        scrapRepository.save(toEntity(command, user, article));
    }

    @Transactional(readOnly = true)
    public List<ScrapDto.Response> findScraps(ScrapDto.Command command, int page, int size){
        User user = userRepository.findById(command.userId()).orElseThrow(() -> new ClickCleanException(UserErrorCode.USER_NOT_FOUND_ERROR));
        List<Scrap> scraps = scrapRepository.findScrapsByUser(user, page, size);
        return toResponseList(scraps);
    }

    private Scrap toEntity(ScrapDto.Command scrapCommand, User user, Article article){
        return Scrap.builder()
                .user(user)
                .article(article)
                .build();
    }
    private ScrapDto.Response toResponse(Scrap scrap){
        Article article = scrap.getArticle();
        return ScrapDto.Response.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .media(article.getMedia())
                .category(article.getCategory().getKoreanName())
                .author(article.getAuthor())
                .createdAt(article.getCreatedAt())
                .probability(article.getProbability())
                .build();
    }
    private List<ScrapDto.Response> toResponseList(List<Scrap> scraps){
        return scraps.stream()
                .map(this::toResponse)
                .toList();
    }
}
