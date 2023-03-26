package com.example.demo.controller;

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
     * Show all account
     */
    @GetMapping("/show")
    public List<UserInfo> show() {
        return userService.accountList();
    }

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
//    public Map<String,Object> createAccount(@RequestBody Map<String, String> objectMap) throws NoSuchAlgorithmException {
//        Map<String,Object> jsonResponse = userService.accountCreate(objectMap.get("username"),objectMap.get("password"));
//        return jsonResponse;
//    }

    /**
     * log in
     */
    @PostMapping("/login")
    public ResponseEntity<Response> verifyIdJPA(@RequestBody Map<String, String> objectMap){
//        System.out.println("Verify Id");
//        System.out.println(objectMap.get("username")+objectMap.get("password"));
//        Response response = userService.accountVerify(objectMap.get("username"),objectMap.get("password"));
//        return ResponseEntity.status(HttpStatus.OK).body(response);
        UserResponse userResponse = userService.accountVerify(objectMap.get("username"), objectMap.get("password"));
        switch (userResponse.statusCode) {
            case 40101:
            case 40103:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false,userResponse.getMessage()));
            case 40102:
                return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(new Response(false,userResponse.getMessage()));
            case 200:
                return ResponseEntity.status(HttpStatus.OK).body(new Response(true,userResponse.getMessage()));
            default:
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response(false, userResponse.getMessage()));
        }

    }
}
