package me.khw7385.click_clean.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScrapResponse {
    private Long articleId;
    private String title;
    private String summary;
    private String media;
    private String category;
    private String author;
    private LocalDateTime createdAt;
    private BigDecimal probability;

    @Builder
    public ScrapResponse(Long articleId, String title, String summary, String media, String category, String author, LocalDateTime createdAt, BigDecimal probability) {
        this.articleId = articleId;
        this.title = title;
        this.summary = summary;
        this.media = media;
        this.category = category;
        this.author = author;
        this.createdAt = createdAt;
        this.probability = probability;
    }
}
