package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class MyController {
    @Autowired
    MyService myService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test")
    public String test() {
        System.out.println("Hi it's on console");
        return "Hi it's on web screen";
    }


    @GetMapping("/testdb")
    public String testDatabase() {
        try {
            String query = "SELECT 1";
            int result = jdbcTemplate.queryForObject(query, Integer.class);
            return "Connected to database successfully";
        } catch (Exception e) {
            return "Failed to connect to database: " + e.getMessage();
        }
    }

    /**
     *Show all account
     */
    @GetMapping("/show")
    public List<UserInfo> show() {
//        return myService.accountList();
        return myService.accountListJPA();
    }

    /**
     *Create account
     */
    @PostMapping("/register")
    @ResponseBody
    public Map<String,String> createAccount(@RequestBody Map<String, String> objectMap){
        Map<String,String> jsonResponse = new HashMap<>();
        if(myService.accountCreate(objectMap.get("username"),objectMap.get("password"))){
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
        if(myService.accountVerify(objectMap.get("username"),objectMap.get("password"))){
            jsonResponse.put("success","verify id success");
        }else {
            jsonResponse.put("fail","verify id fail");
        };
        return jsonResponse;
    }
}
