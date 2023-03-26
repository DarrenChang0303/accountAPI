package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts;

    @Column(name = "last_attempt_time")
    private Date lastAttemptTime;

    public UserInfo(){

    }
    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public UserInfo(String name, String password, int failedAttempts, Date lastAttemptTime) {
        this.name = name;
        this.password = password;
        this.failedAttempts = failedAttempts;
        this.lastAttemptTime = lastAttemptTime;
    }
}
