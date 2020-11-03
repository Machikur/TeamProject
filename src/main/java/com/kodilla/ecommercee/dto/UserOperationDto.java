package com.kodilla.ecommercee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserOperationDto {
    private Long id;
    private Long userId;
    private String operationName;
    private LocalDateTime operationTime;

    public UserOperationDto(Long userId, String operationName) {
        this.userId = userId;
        this.operationName = operationName;
    }
}
