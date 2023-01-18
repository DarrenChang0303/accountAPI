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

    public boolean accountCreate(String username, String password) {
        boolean pass = true;
        //Todo:refactor checkPassWord
//        if (username.length() < 3 || username.length() > 32) {
//            System.out.println("The number of user name characters should be between 3 to 32");
//            return false;
//        } else
            if (checkPassWord(username,password)) {
            User user = new User(username, password);
            userArray.add(user);
            System.out.println("Account create successfully");
        }else {
            pass = false;
        }
            return pass;
    }

    public boolean checkPassWord(String username,String password) {
//        String pattern = "[A-Z]+[a-z]+[0-9]+";
//        String pattern = "/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])/";
//        String pattern = ".*[0-9]{1,}.*";
//        String pattern = ".*(.*[0-9]{1,}.*).*(.*[A-Z]{1,}.*).*(.*[a-z]{1,}.*)";
            String patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$";
            String patternUserName = "^{3,32}$";//need fix
//        Pattern pattern = Pattern.compile(patternPassword);
//        String pattern1 = "(.*[a-z]{1,}.*)";
//        String pattern2 = "(.*[A-Z]{1,}.*)";
//        String pattern3 = "(.*[0-9]{1,}.*)";

//        if (password.length() >= 8 && password.length() <= 32) {
            if (Pattern.matches(patternPassword, password)&&3<=username.length()&&username.length()<=32) {
                return true;
            } else {
//                System.out.println("password content wrong");
                return false;
            }
        }
//        else {
//            System.out.println("password length wrong");
//            return false;
//        }
//        return Pattern.matches(pattern, password);
//        return pattern1.matcher(password).matches();
//    }

//    @Override
//    public boolean equals(Object o){
//
//}
    public boolean accountVerify(String username, String password) {
        User user = new User(username,password);
        return userArray.contains(user);
    }
}