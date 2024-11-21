package me.khw7385.click_clean.dto;

import lombok.Builder;
import lombok.Getter;
import me.khw7385.click_clean.domain.Scrap;

@Getter
public class ScrapRequest {
    private Long user_id;
    private Long article_id;
    private String url;
    @Builder
    public ScrapRequest(Long user_id, Long article_id, String url) {
        this.user_id = user_id;
        this.article_id = article_id;
        this.url = url;
    }
}
