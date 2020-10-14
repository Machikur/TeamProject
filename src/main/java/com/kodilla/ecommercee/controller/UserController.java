package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.UserDto;
import com.kodilla.ecommercee.exception.user.KeyException;
import com.kodilla.ecommercee.exception.user.UserConflictException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserDbService service;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "checkKeyValidity")
    public String checkKeyValidityById(@RequestParam long userId) throws UserNotFoundException, KeyException {
        return service.checkValidityById(userId);
    }

    @PostMapping(value = "createUser")
    public void createUser(@RequestBody UserDto userDto) throws UserConflictException {
        service.addNewUser(userMapper.mapUserDtoToUser(userDto));
    }

    @PutMapping(value = "blockUser")
    public UserDto blockUser(@RequestParam long userId) throws UserNotFoundException {
        return userMapper.mapUserToUserDto(service.blockUser(userId));
    }

    @PutMapping(value = "createUserKey")
    public String createUserKey(@RequestParam String username, @RequestParam String password) throws UserNotFoundException, KeyException, UserConflictException {
        return service.createKeyForUser(username, password);
    }
}
