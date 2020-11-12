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

    private final UserDbService service;
    private final UserOperationService userOperationService;

    @Autowired
    public UserController(UserDbService service, UserOperationService userOperationService) {
        this.service = service;
        this.userOperationService = userOperationService;
    }

    @GetMapping("/listOfUserOperations")
    public List<UserOperationDto> getListOfUserOperations(Long userId) {
        return userOperationService.getListOfUserOperations(userId);
    }


    @GetMapping("/checkKeyValidity")
    public String checkKeyValidityById(@RequestParam Long userId) throws UserNotFoundException, KeyException {
        return service.checkValidityById(userId);
    }

    @PostMapping("/user")
    public UserDto createUser(@RequestBody UserDto userDto) throws UserConflictException {
        return service.addNewUser(userDto);
    }

    @PutMapping("/blockUser")
    public UserDto blockUser(@RequestParam Long userId) throws UserNotFoundException {
        return service.blockUser(userId);
    }

    @PutMapping("/userKey")
    public String createUserKey(@RequestParam String username, @RequestParam String password) throws UserNotFoundException, KeyException, UserConflictException {
        return service.createKeyForUser(username, password);
    }
}
