package edu.java.bot.controller;

import edu.java.bot.exceptions.NoSuchChatException;
import edu.java.bot.model.controller.exceptions.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> nonValidMethodArgumentException(MethodArgumentNotValidException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Invalid request param")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getDetailMessageCode())
                .stacktrace(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList())
                .build());
    }

    @ExceptionHandler(NoSuchChatException.class)
    public ResponseEntity<ApiErrorResponse> noSuchChatException(NoSuchChatException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Chat not found")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .stacktrace(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList())
                .build());
    }
}
