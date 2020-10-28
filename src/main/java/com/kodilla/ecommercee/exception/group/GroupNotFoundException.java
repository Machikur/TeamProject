package com.kodilla.ecommercee.exception.group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroupNotFoundException extends Exception {
    private final static String message = "Grupa o podanym ID nie istnieje";

    public GroupNotFoundException() {
        super(message);
    }
}
