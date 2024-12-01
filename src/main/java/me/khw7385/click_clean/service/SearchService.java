package me.khw7385.click_clean.service;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.dto.SearchDto;
import me.khw7385.click_clean.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<SearchDto.Response> searchArticles(SearchDto.Command command, int page, int size){
        List<Article> foundArticles = articleRepository.findArticlesByKeywordOrderByCreatedAtDesc(command.keyword(), page, size);
        return toResponseList(foundArticles);
    }
    private SearchDto.Response toResponse(Article article){
        return SearchDto.Response.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .author(article.getAuthor())
                .media(article.getMedia())
                .category(article.getCategory())
                .createdAt(article.getCreatedAt())
                .probability(article.getProbability())
                .build();
    }
    private List<SearchDto.Response> toResponseList(List<Article> articles){
        return articles.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
