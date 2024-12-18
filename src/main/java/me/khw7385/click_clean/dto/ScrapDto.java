package me.khw7385.click_clean.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ScrapDto {
    public record Request(Long articleId){
        public Command toCommand(Long scrapId, Long userId){
            return new Command(scrapId, userId, articleId);
        }
    }
    public record Command(Long scrapId, Long userId, Long articleId){
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Response(Long id, String title, String summary, String media, String category, String author, LocalDateTime createdAt, BigDecimal probability){
        @Builder
        public Response(Long id, String title, String summary, String media, String category, String author, LocalDateTime createdAt, BigDecimal probability) {
            this.id = id;
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
