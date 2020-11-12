package com.kodilla.ecommercee.exception.group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GroupConflictException extends Exception {
    private final static String message = "Grupa o podanej nazwie juz istnieje";

    public GroupConflictException() {
        super(message);
    }
}
