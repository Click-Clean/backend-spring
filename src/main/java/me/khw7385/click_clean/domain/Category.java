package me.khw7385.click_clean.domain;

import lombok.Getter;

@Getter
public enum Category {
    POLITICS("정치"),
    ECONOMY("경제"),
    SOCIETY("사회"),
    LIFE_CULTURE("생활/문화"),
    IT_SCIENCE("IT/과학"),
    WORLD("세계");

    private String koreanName;

    Category(String koreanName) {
        this.koreanName = koreanName;
    }
}
