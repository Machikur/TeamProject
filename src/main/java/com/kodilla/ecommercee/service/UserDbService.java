package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.user.KeyException;
import com.kodilla.ecommercee.exception.user.UserConflictException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserDbService {

    @Autowired
    private UserDao userDao;

    public void addNewUser(User user) throws UserConflictException {
        if (!userDao.existsById(user.getUserId()) && !userDao.existsByUsername(user.getUsername())) {
            userDao.save(user);
            log.info("Zapisano użytkownika " + user.getUsername() + ", o numerze ID " + user.getUserId());
        } else {
            throw new UserConflictException("Użytkownik już istnieje");
        }
    }

    public User blockUser(long userId) throws UserNotFoundException {
        User user = findById(userId);
        user.setEnable(false);
        updateUser(user);
        log.info("Użytkownik " + user.getUsername() + " został zablokowany");
        return user;
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

    public String checkValidityById(long userId) throws UserNotFoundException, KeyException {
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
        String message = "Wytworzono klucz dla użytkownika " + user.getUsername() + ": " + key;
        log.info(message);
        return message;
    }

    private User findById(long userId) throws UserNotFoundException {
        return userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("Użytkownik nie istnieje lub podano błędne dane"));
    }

    private void updateUser(User user) {
        userDao.save(user);
        log.info("Uaktualniono użytkownika: " + user.getUsername() + ", o numerze Id: " + user.getUserId());
    }

    private User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        return userDao.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new UserNotFoundException("Użytkownik nie istnieje lub podano błędne dane"));
    }
}
