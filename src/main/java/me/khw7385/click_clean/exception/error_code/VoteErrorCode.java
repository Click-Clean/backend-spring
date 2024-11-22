package me.khw7385.click_clean.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode implements ErrorCode{
    VOTE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "V001", "투표 데이터를 찾을 수 없습니다."),
    DUPLICATE_VOTE_VALUE_ERROR(HttpStatus.CONFLICT, "V002", "중복된 투표 값을 가지고 있습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
