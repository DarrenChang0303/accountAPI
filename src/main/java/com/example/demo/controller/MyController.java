package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.Service;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.json.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
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
    @PostMapping("/register")
    @ResponseBody
    public Map<String,String> createAccount(@RequestBody Map<String, String> objectMap){
        Map<String,String> jsonResponse = new HashMap<>();
        if(service.accountCreate(objectMap.get("username"),objectMap.get("password"))){
            jsonResponse.put("success","create account successful");
        }else {
            jsonResponse.put("false","create account fail");
        }
        return jsonResponse;
    }

    @PostMapping("/login")
    public boolean vertifyId(@RequestBody Map<String, String> objectMap) {
        System.out.println("Verify Id");
        System.out.println(objectMap.get("username")+objectMap.get("password"));
        return service.accountVerify(objectMap.get("username"),objectMap.get("password"));
    }
}
