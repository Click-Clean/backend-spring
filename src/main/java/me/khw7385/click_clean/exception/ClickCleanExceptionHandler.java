package me.khw7385.click_clean.exception;

import me.khw7385.click_clean.dto.ErrorResponse;
import me.khw7385.click_clean.exception.error_code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClickCleanExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClickCleanException.class)
    public ErrorResponse handler(ClickCleanException e){
        ErrorCode errorCode = e.getErrorCode();
        return new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getCode(), errorCode.getMessage());
    }
}
