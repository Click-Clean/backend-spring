package me.khw7385.click_clean.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import me.khw7385.click_clean.domain.Category;

import java.math.BigDecimal;

public class ViewDto {
    public record Request(){

    }
    public record Command(String userViewId, Long articleId){
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ArticleResponse(Long articleId, String title, String body, String media, String category, String author, String url, BigDecimal probability){
        @Builder
        public ArticleResponse(Long articleId, String title, String body, String media, String category, String author, String url, BigDecimal probability) {
            this.articleId = articleId;
            this.title = title;
            this.body = body;
            this.media = media;
            this.category = category;
            this.author = author;
            this.url = url;
            this.probability = probability;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ViewResponse(Long articleId, String title){
        @Builder
        public ViewResponse(Long articleId, String title) {
            this.articleId = articleId;
            this.title = title;
        }
    }
}
