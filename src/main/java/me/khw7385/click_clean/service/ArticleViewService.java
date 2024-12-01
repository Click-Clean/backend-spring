package me.khw7385.click_clean.service;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.dto.ViewDto;
import me.khw7385.click_clean.exception.ClickCleanException;
import me.khw7385.click_clean.exception.error_code.ArticleErrorCode;
import me.khw7385.click_clean.repository.ArticleRepository;
import me.khw7385.click_clean.repository.ArticleViewRedisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewRedisRepository articleViewRedisRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public ViewDto.ArticleResponse viewArticle(ViewDto.Command command){
        Article article = articleRepository.findById(command.articleId()).orElseThrow(() -> new ClickCleanException(ArticleErrorCode.ARTICLE_NOT_FOUND_ERROR));

        boolean hasView = articleViewRedisRepository.isViewed(command.userViewId(), command.articleId());

        if(hasView){
            articleViewRedisRepository.extendExpiration(command.userViewId(), command.articleId());
        }else{
            articleViewRedisRepository.saveView(command.userViewId(), command.articleId());
            articleViewRedisRepository.incrementViewCount(command.articleId());
        }

        return toArticleResponse(article);
    }

    @Transactional(readOnly = true)
    public List<ViewDto.ViewResponse> findArticlesTop5(ViewDto.Command command){
        List<Long> articleIds = articleViewRedisRepository.findArticlesTop5();
        List<Article> articles = articleRepository.findArticlesByIds(articleIds);

        return toViewResponseList(articles);
    }

    private ViewDto.ArticleResponse toArticleResponse(Article article){
        return ViewDto.ArticleResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .author(article.getAuthor())
                .media(article.getMedia())
                .category(article.getCategory())
                .url(article.getUrl())
                .probability(article.getProbability())
                .build();
    }

    private List<ViewDto.ViewResponse> toViewResponseList(List<Article> articles){
        return articles.stream()
                .map(this::toViewResponse)
                .collect(Collectors.toList());
    }

    private ViewDto.ViewResponse toViewResponse(Article article){
        return ViewDto.ViewResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .build();
    }
}
