package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.UserDto;
import com.kodilla.ecommercee.dto.UserOperationDto;
import com.kodilla.ecommercee.exception.user.KeyException;
import com.kodilla.ecommercee.exception.user.UserConflictException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.service.UserDbService;
import com.kodilla.ecommercee.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserDbService service;

    @Autowired
    private UserOperationService userOperationService;

    @GetMapping(value = "getListOfUserOperations")
    public List<UserOperationDto> getListOfUserOperations(Long userId) {
        return userOperationService.getListOfUserOperations(userId);
    }


    @GetMapping(value = "checkKeyValidity")
    public String checkKeyValidityById(@RequestParam Long userId) throws UserNotFoundException, KeyException {
        return service.checkValidityById(userId);
    }

    @PostMapping(value = "createUser")
    public UserDto createUser(@RequestBody UserDto userDto) throws UserConflictException {
        return service.addNewUser(userDto);
    }

    @PutMapping(value = "blockUser")
    public UserDto blockUser(@RequestParam Long userId) throws UserNotFoundException {
        return service.blockUser(userId);
    }

    @PutMapping(value = "createUserKey")
    public String createUserKey(@RequestParam String username, @RequestParam String password) throws UserNotFoundException, KeyException, UserConflictException {
        return service.createKeyForUser(username, password);
    }
}
