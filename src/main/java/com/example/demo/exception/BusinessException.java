package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    HttpStatus status;

    public BusinessException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
