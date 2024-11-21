package me.khw7385.click_clean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public record Result(int status, Object data) {
}
