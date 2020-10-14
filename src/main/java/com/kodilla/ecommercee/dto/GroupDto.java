package com.kodilla.ecommercee.dto;

import lombok.Getter;

@Getter
public class GroupDto {
    private final Long groupId;
    private final String name;

    public GroupDto(Long groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }
}