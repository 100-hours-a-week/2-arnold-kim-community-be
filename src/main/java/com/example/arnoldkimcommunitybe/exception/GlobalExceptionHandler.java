package com.example.arnoldkimcommunitybe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConfilctException.class)
    public ResponseEntity<Map<String, Object>> handleConflictException(ConfilctException ex) {
        // 예외로부터 필요한 정보를 꺼내, JSON 형태로 구성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "conflict"); // 원하는 필드명
        responseBody.put("errorDetails", ex.getErrorDetails()); // ConfilctException에서 꺼낸 내용

        return ResponseEntity.status(HttpStatus.CONFLICT)  // 409
                .body(responseBody);
    }
}