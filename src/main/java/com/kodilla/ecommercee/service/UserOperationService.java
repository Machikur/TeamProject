package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserOperation;
import com.kodilla.ecommercee.dto.UserOperationDto;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserOperationMapper;
import com.kodilla.ecommercee.repository.UserOperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOperationService {

    private UserOperationMapper userOperationMapper;
    private UserDbService userDbService;
    private UserOperationDao userOperationDao;

    @Autowired
    public UserOperationService(UserOperationMapper userOperationMapper, UserDbService userDbService, UserOperationDao userOperationDao) {
        this.userOperationMapper = userOperationMapper;
        this.userDbService = userDbService;
        this.userOperationDao = userOperationDao;
    }

    public void save(UserOperationDto userOperationDto) throws UserNotFoundException {
        UserOperation userOperation = userOperationMapper.mapToUserOperation(userOperationDto);
        User user = userDbService.findById(userOperationDto.getUserId());
        userOperation.setUser(user);
        userOperationDao.save(userOperation);
    }

    public List<UserOperationDto> getListOfUserOperations(Long userId) {
        List<UserOperation> userOperations = userOperationDao.findAllByUser_UserId(userId);
        return userOperationMapper.mapToDtoList(userOperations);
    }
}
