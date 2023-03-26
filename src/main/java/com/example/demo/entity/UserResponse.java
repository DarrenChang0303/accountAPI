package com.example.demo.entity;

import lombok.Data;

@Data
public class UserResponse {
    public int statusCode;
    public String message;

    public UserResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public UserResponse(int statusCode){
        this.statusCode = statusCode;
    }

}
