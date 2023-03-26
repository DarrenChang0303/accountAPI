package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.entity.Response;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Create account
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Response> createAccount(@RequestBody Map<String, String> objectMap) {

        UserResponse userResponse = userService.accountCreate(objectMap.get("username"), objectMap.get("password"));

        switch (userResponse.statusCode) {
            case 200:
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new Response(true, userResponse.getMessage()));
            case 400:
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new Response(false, userResponse.getMessage()));

            case 401:
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new Response(false, userResponse.getMessage()));
            default:
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response(false, userResponse.getMessage()));
        }
    }

    /**
     * log in
     */
    @PostMapping("/login")
    public ResponseEntity<Response> verifyIdJPA(@RequestBody Map<String, String> objectMap) {
        UserResponse userResponse = userService.accountVerify(objectMap.get("username"), objectMap.get("password"));
        switch (userResponse.statusCode) {
            case 40101:
            case 40103:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false, "Invalid username or password."));
            case 40102:
                return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(new Response(false, userResponse.getMessage()));
            case 200:
                return ResponseEntity.status(HttpStatus.OK).body(new Response(true, userResponse.getMessage()));
            default:
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response(false, userResponse.getMessage()));
        }

    }
}
