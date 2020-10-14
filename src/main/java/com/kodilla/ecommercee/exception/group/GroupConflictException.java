package com.kodilla.ecommercee.exception.group;

public class GroupConflictException extends Exception {
    private final static String message = "Grupa o podanym ID juz istnieje";

    public GroupConflictException() {
        super(message);
    }
}
