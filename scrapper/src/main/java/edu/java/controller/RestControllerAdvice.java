package edu.java.controller;

import edu.java.ApiErrorResponse;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.LinkAlreadyExistsException;
import edu.java.exceptions.NoSuchChatException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> notValidMethodArgument(MethodArgumentNotValidException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Некорректные параметры запроса")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getDetailMessageCode())
                .stacktrace(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList())
                .build());
    }

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatAlreadyExists(ChatAlreadyRegisteredException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Chat already exists")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .stacktrace(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList())
                .build());
    }

    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyExists(LinkAlreadyExistsException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Link already exists")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .stacktrace(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList())
                .build());
    }

    @ExceptionHandler(NoSuchChatException.class)
    public ResponseEntity<ApiErrorResponse> noSuchChatException(NoSuchChatException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("No such chat exception")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .stacktrace(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList())
                .build());
    }
}
