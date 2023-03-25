package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
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
    @PostMapping("/registerJPA")
    @ResponseBody
    public Map<String,Object> createAccountJpa(@RequestBody Map<String, String> objectMap) throws NoSuchAlgorithmException {
        Map<String,Object> jsonResponse = myService.accountCreateJPA(objectMap.get("username"),objectMap.get("password"));
        return jsonResponse;
    }

    /**
     *log in
     */
    @PostMapping("/loginJPA")
    public Map<String,Object> verifyIdJPA(@RequestBody Map<String, String> objectMap) throws NoSuchAlgorithmException {
//        Map<String,Object> jsonResponse = myService.accountVerifyJPA(objectMap.get("username"),objectMap.get("password"));
        System.out.println("Verify Id");
        System.out.println(objectMap.get("username")+objectMap.get("password"));
        return myService.accountVerifyJPA(objectMap.get("username"),objectMap.get("password"));
    }
}
