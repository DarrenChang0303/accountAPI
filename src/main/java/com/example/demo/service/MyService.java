package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class MyService {
    @Autowired
    private UserRepository userRepository;

    public List<UserInfo> accountListJPA() {
        return userRepository.findAll();
    }

    public Map<String, Object> accountCreateJPA(String username, String password) {
        Map<String, Object> resultMap = checkProcess(username, password);
        if (resultMap.get("success") == null) {
            resultMap.put("success", true);
            resultMap.put("reason", "Create user successful");
            UserInfo user = new UserInfo(username,password);
            userRepository.save(user);
            return resultMap;
        } else {
            return resultMap;
        }
    }

    private Map<String, Object> checkProcess(String username, String password) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$";
        if (userRepository.existsByName(username)) {
            resultMap.put("success", false);
            resultMap.put("reason", "Username already exists");
            return resultMap;
        } else {
            if (username.length() < 3 || username.length() > 32) {
                resultMap.put("success", false);
                resultMap.put("reason", "Username length should be between 3 and 32 characters");
                return resultMap;
            }
            if (password.length() < 8 || password.length() > 32) {
                resultMap.put("success", false);
                resultMap.put("reason", "Password length should be between 8 and 32 characters");
                return resultMap;
            }
            if (!Pattern.matches(patternPassword, password)) {
                resultMap.put("success", false);
                resultMap.put("reason", "Password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number");
                return resultMap;
            }
        }
        return resultMap;
    }

    public Map<String, Object> accountVerifyJPA(String username, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        String userTemp = username;
        if (!userRepository.existsByName(username)) {
            resultMap.put("success", false);
            resultMap.put("reason", "Wrong user name");
        } else if (userRepository.findByName(username).get(0).getPassword() == password) {
            resultMap.put("success", true);
            resultMap.put("reason", "Log in successful");
        }
        return resultMap;
    }
}