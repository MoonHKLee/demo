package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAllowedException extends BusinessException {
    public NotAllowedException(String msg, HttpStatus status) {
        super(msg,status);
    }
}
