package com.kodilla.ecommercee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private CartDto cartDto;
    private List<OrderDto> ordersDto = new ArrayList<>();
    private boolean isEnable;
    private LocalDateTime keyTimeCreated;

}
