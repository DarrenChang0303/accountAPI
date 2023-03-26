package com.example.demo.entity;

import lombok.Data;

@Data
public class Response {


    public boolean success;
    public String reason;

    public Response(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public Response(boolean success) {
        this.success = success;
    }

    public Response() {

    }

    public boolean isSuccess() {
        return success;
    }
}
