package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service
public class Service {

    Map<String, String> userMap;
    ArrayList<User> userArray = new ArrayList<User>();

    public ArrayList accountList() {
        if (userArray.size() != 0) {
            for (User user : userArray) {
                System.out.println("user" + user.getUserName());
            }
            return userArray;
        } else {
            return null;
        }
    }

    public boolean accountCreate(String username, String password) {
        boolean pass = true;
        if (checkPassWord(username, password)) {
            User user = new User(username, password);
            userArray.add(user);
            System.out.println("Account create success");
        } else {
            pass = false;
        }
        return pass;
    }

    public boolean checkPassWord(String username, String password) {
        String patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$";

        if (Pattern.matches(patternPassword, password) && 3 <= username.length() && username.length() <= 32) {
            return true;
        } else {
            return false;
        }
    }

    public boolean accountVerify(String username, String password) {
        User user = new User(username, password);
        return userArray.contains(user);
    }
}