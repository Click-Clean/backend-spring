package me.khw7385.click_clean.dto;

public record PageResult<T>(int status, int page, int size, T data) {
}
