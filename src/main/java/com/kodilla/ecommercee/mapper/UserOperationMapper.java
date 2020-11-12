package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.UserOperation;
import com.kodilla.ecommercee.dto.UserOperationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserOperationMapper {

    private final UserMapper userMapper;

    @Autowired
    public UserOperationMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserOperation mapToUserOperation(UserOperationDto userOperationDto) {
        return new UserOperation(
                userOperationDto.getOperationName()
        );
    }

    public UserOperationDto mapToUserOperationDto(UserOperation userOperation) {
        return new UserOperationDto(
                userOperation.getId(),
                userOperation.getUser().getUserId(),
                userOperation.getOperationName(),
                userOperation.getOperationTime()
        );
    }

    public List<UserOperationDto> mapToDtoList(List<UserOperation> list) {
        return list.stream()
                .map(this::mapToUserOperationDto)
                .collect(Collectors.toList());
    }
}
