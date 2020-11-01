package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDto;
import com.kodilla.ecommercee.exception.user.KeyException;
import com.kodilla.ecommercee.exception.user.UserConflictException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.UserDao;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
@Transactional
public class UserDbService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    public UserDto addNewUser(UserDto userDto) throws UserConflictException {
        if (!userDao.existsByUsername(userDto.getUsername())) {
            User user = userMapper.mapUserDtoToUser(userDto);
            Cart cart = new Cart();
            user.setCart(cart);
            cart.setUser(user);
            user.setOrders(new ArrayList<>());
            return userMapper.mapUserToUserDto(userDao.save(user));
        } else {
            throw new UserConflictException("Użytkownik już istnieje");
        }
    }

    public UserDto blockUser(Long userId) throws UserNotFoundException {
        User user = findById(userId);
        user.setEnable(false);
        updateUser(user);
        return userMapper.mapUserToUserDto(user);
    }

    public String createKeyForUser(String username, String password) throws UserNotFoundException, KeyException, UserConflictException {
        User user = findUserByUsernameAndPassword(username, password);
        if (!user.isEnable()) {
            throw new UserConflictException("Użytkownik zablokowany");
        }
        if (checkKeyValidityForUser(user)) {
            throw new KeyException("Uzytkownik posiada już ważny klucz");
        } else {
            return generateKeyForUser(user);
        }
    }

    public String checkValidityById(Long userId) throws UserNotFoundException, KeyException {
        User user = findById(userId);
        if (checkKeyValidityForUser(user) && user.isEnable()) {
            return "Użytkownik: " + user.getUsername() + " posiada ważny klucz: " + user.getUserKey();
        } else
            throw new KeyException("Klucz nie istnieje lub wygasł");
    }

    private boolean checkKeyValidityForUser(User user) {
        return user.getUserKey() != null && user.getKeyTimeCreated().plusHours(1).isAfter(LocalDateTime.now());
    }

    private String generateKeyForUser(User user) {
        String key = RandomString.make(10);
        user.setUserKey(key);
        updateUser(user);
        return "Wytworzono klucz dla użytkownika " + user.getUsername() + ": " + key;
    }

    public User findById(Long userId) throws UserNotFoundException {
        return userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("Użytkownik nie istnieje"));
    }

    private void updateUser(User user) {
        userDao.save(user);
    }

    private User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        return userDao.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new UserNotFoundException("Użytkownik nie istnieje lub podano błędne dane"));
    }
}
