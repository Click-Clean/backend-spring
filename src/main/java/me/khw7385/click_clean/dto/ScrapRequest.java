package me.khw7385.click_clean.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapRequest {
    private Long userId;
    private Long articleId;
    @Builder
    public ScrapRequest(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }
}
