package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public UserMapper(CartMapper cartMapper, OrderMapper orderMapper) {
        this.cartMapper = cartMapper;
        this.orderMapper = orderMapper;
    }

    public User mapUserDtoToUser(UserDto userDto) {
        return new User(
                userDto.getUsername(),
                userDto.getPassword()
        );
    }

    public UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                cartMapper.mapToCartDto(user.getCart()),
                orderMapper.mapToOrderDtoList(user.getOrders()),
                user.isEnable(),
                user.getKeyTimeCreated()
        );
    }
}
