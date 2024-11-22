package me.khw7385.click_clean.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SearchDto {
    public record Request(String keyword){
    }
    public record Command(String keyword){
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Response(Long articleId, String title, String summary, String media, String category, String author, LocalDateTime createdAt, BigDecimal probability){
        @Builder
        public Response(Long articleId, String title, String summary, String media, String category, String author, LocalDateTime createdAt, BigDecimal probability) {
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
}
