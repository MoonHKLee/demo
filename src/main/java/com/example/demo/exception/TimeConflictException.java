package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TimeConflictException extends BusinessException {
    public TimeConflictException(String msg, HttpStatus status) {
        super(msg,status);
    }
}
