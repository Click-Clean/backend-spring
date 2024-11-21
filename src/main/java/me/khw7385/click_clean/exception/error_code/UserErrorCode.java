package me.khw7385.click_clean.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "U001", "존재하지 않은 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
