package com.kodilla.ecommercee.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class KeyException extends Exception {
    public KeyException(String message) {
        super(message);
    }
}
