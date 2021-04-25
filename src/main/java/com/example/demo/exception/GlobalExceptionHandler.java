package com.example.demo.exception;

import com.example.demo.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<Response> notAllowedException(BusinessException e) {
        Response response = Response.builder()
            .message(e.getMessage())
            .body(null)
            .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(TimeConflictException.class)
    public ResponseEntity<Response> timeConflictException(BusinessException e) {
        Response response = Response.builder()
            .message(e.getMessage())
            .body(null)
            .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> businessException(BusinessException e) {
        Response response = Response.builder()
            .message(e.getMessage())
            .body(null)
            .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }
}
