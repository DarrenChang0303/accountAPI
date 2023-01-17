package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.Service;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.json.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class MyController {
    @Autowired
    Service service;

    @GetMapping("/test")
    public String test() {
        System.out.println("Hi it's on console");
        return "Hi it's on web screen";
    }

    /**
     *Show all account
     */
    @GetMapping("/show")
    public ArrayList show() {
        return service.accountList();
    }

    /**
     *Create account
     */
    @PostMapping("/create")
    public String createId(@RequestBody Map<String, String> objectMap) {
        System.out.println("Start to create account");
        String username = objectMap.get("username");
        String password = objectMap.get("password");
        System.out.println("name:" + username);
        System.out.println("pass:" + password);
        service.accountCreate(username, password);
        return "success";
    }

    @PostMapping("/verify")
    public String vertifyId() {
        System.out.println("Verify Id");
        return "Verifying Id";
    }
}
