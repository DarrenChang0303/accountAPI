package com.example.demo.controller;

import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            jsonResponse.put("success","create account success");
        }else {
            jsonResponse.put("false","create account fail");
        }
        return jsonResponse;
    }

    /**
     *log in
     */
    @PostMapping("/login")
    public Map<String,String> verifyId(@RequestBody Map<String, String> objectMap) {
        Map<String,String> jsonResponse = new HashMap<>();
        System.out.println("Verify Id");
        System.out.println(objectMap.get("username")+objectMap.get("password"));
        if(service.accountVerify(objectMap.get("username"),objectMap.get("password"))){
            jsonResponse.put("success","verify id success");
        }else {
            jsonResponse.put("fail","verify id fail");
        };
        return jsonResponse;
    }
}
