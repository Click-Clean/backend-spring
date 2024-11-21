package me.khw7385.click_clean.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.exception.error_code.ErrorCode;

@Getter
@RequiredArgsConstructor
public class ClickCleanException extends RuntimeException{
    private final ErrorCode errorCode;
}
