package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoUserException extends BusinessException {
    public NoUserException(String msg, HttpStatus status) {
        super(msg,status);
    }
}
