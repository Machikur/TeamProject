package com.kodilla.ecommercee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private long userId;
    private String username;
    private String password;
    private CartDto cartDto;
    private List<OrderDto> ordersDto = new ArrayList<>();


    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
