package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MyController {
    @GetMapping("/test")
    public String test() {
//        System.out.println("Hi it's on console");
        return "Hi it's on web screen";
    }

    @PostMapping("/create")
    public String createId() {
        System.out.println("Start to create account");
        return "Creating user account";

    }
}
