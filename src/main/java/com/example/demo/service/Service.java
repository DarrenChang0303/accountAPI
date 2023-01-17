package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service
public class Service {

    Map<String, String> userMap;
    ArrayList<User> userArray = new ArrayList<User>();

    public void accountSave(String userName, String password) {

    }

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

    public void accountCreate(String username, String password) {

        if (username.length() < 3 || username.length() > 32) {
            System.out.println("The number of user name characters should be between 3 to 32");
        } else if (checkPassWord(password)) {
            User user = new User(username, password);
            userArray.add(user);
            System.out.println("Account create successfully");
        }
    }

    public boolean checkPassWord(String password) {
//        String pattern = "[A-Z]+[a-z]+[0-9]+";
//        String pattern = "/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])/";
//        String pattern = ".*[0-9]{1,}.*";
//        String pattern = ".*(.*[0-9]{1,}.*).*(.*[A-Z]{1,}.*).*(.*[a-z]{1,}.*)";
//        String pattern = "(.*[a-z][0-9]{1,}.*)";
        String pattern1 = "(.*[a-z]{1,}.*)";
        String pattern2 = "(.*[A-Z]{1,}.*)";
        String pattern3 = "(.*[0-9]{1,}.*)";

        if (password.length() >= 8 && password.length() <= 32) {
            if (Pattern.matches(pattern1, password) && Pattern.matches(pattern2, password) && Pattern.matches(pattern3, password)) {
                return true;
            } else {
                System.out.println("password content wrong");
                return false;
            }
        } else {
            System.out.println("password length wrong");
            return false;
        }
//        return Pattern.matches(pattern, password);
    }

    public void accountVerify(String username, String password) {

    }
}
