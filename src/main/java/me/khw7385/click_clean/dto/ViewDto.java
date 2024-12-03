package me.khw7385.click_clean.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

public class ViewDto {
    public record Request(){

    }
    public record Command(String userViewId, Long articleId){
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ArticleResponse(Long id, String title, String body, String media, String category, String author, String url, BigDecimal probability){
        @Builder
        public ArticleResponse(Long id, String title, String body, String media, String category, String author, String url, BigDecimal probability) {
            this.id = id;
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
    public record ViewResponse(Long id, String title){
        @Builder
        public ViewResponse(Long id, String title) {
            this.id = id;
            this.title = title;
        }
    }
}
