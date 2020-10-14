package com.kodilla.ecommercee.exception.group;

public class GroupNotFoundException extends Exception {
    private final static String message = "Grupa o podanym ID nie istnieje";

    public GroupNotFoundException() {
        super(message);
    }
}
