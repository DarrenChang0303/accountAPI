package com.example.demo.service;

import com.example.demo.controller.UserController;
import com.example.demo.entity.Message;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public List<UserInfo> accountList() {
        return userRepository.findAll();
    }

    public UserResponse accountCreate(String username, String password) {
        UserResponse userResponse = checkProcess(username, password);
        if (userResponse.statusCode == 200) {
            String hashPassword = passwordService.hashPassword(password);
            UserInfo user = new UserInfo(username, hashPassword);
            userRepository.save(user);
            userResponse.setMessage(Message.CREATE_SUCCESSFUL);
        }
        return userResponse;
    }

    private UserResponse checkProcess(String username, String password) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
        String patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$";
        if (userRepository.existsByName(username)) {
            return new UserResponse(400, Message.USERNAME_EXISTS);
        } else {
            if (username.length() < 3 || username.length() > 32) {
                return new UserResponse(400, Message.INVALID_USERNAME_LENGTH);
            }
            if (password.length() < 8 || password.length() > 32) {
                return new UserResponse(400, Message.INVALID_PASSWORD_LENGTH);
            }
            if (!Pattern.matches(patternPassword, password)) {
                return new UserResponse(400, Message.INVALID_PASSWORD_FORMAT);
            }
        }
        return new UserResponse(200);
    }

    public UserResponse accountVerify(String userName, String password) {
        Optional<UserInfo> optionalUser = userRepository.findByName(userName);
        if (!optionalUser.isPresent()) {
            return new UserResponse(40101, Message.INVALID_USERNAME);
        }
        UserInfo userInfo = optionalUser.get();
        if (overMaxFailAttemps(userInfo)) {
            return new UserResponse(40102, Message.FAILED_ATTEMPTS);
        }
        if (!passwordService.verifyPassword(userInfo.getPassword(), password)) {
            updateFailedAttempts(userInfo);
            return new UserResponse(40103, Message.INVALID_PASSWORD);
        }
        resetFailedAttempts(userInfo);
        return new UserResponse(200, Message.LOGIN_SUCCESS);
    }


    private boolean overMaxFailAttemps(UserInfo userInfo) {
        int failedAttempts = userInfo.getFailedAttempts();
        return failedAttempts >= 5 &&
                userInfo.getLastAttemptTime() != null &&
                System.currentTimeMillis() - userInfo.getLastAttemptTime().getTime() < 60 * 1000;
    }

    private void updateFailedAttempts(UserInfo userInfo) {
        int failedAttempts = userInfo.getFailedAttempts() + 1;
        userInfo.setFailedAttempts(failedAttempts);
        if (failedAttempts == 5) {
            userInfo.setLastAttemptTime(new Date(System.currentTimeMillis()));
        }
        if (failedAttempts > 5) {
            resetFailedAttempts(userInfo);
        }
        LOG.info("{} failed attempts: {}", userInfo.getName(), failedAttempts);
        userRepository.save(userInfo);
    }

    private void resetFailedAttempts(UserInfo userInfo) {
        userInfo.setFailedAttempts(0);
        userInfo.setLastAttemptTime(null);
        userRepository.save(userInfo);
    }
}