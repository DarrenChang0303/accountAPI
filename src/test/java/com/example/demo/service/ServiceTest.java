package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ServiceTest {
    @Autowired
    Service service;

    @Test
    public void testCheckPassword(){
        String test1 = "1234";
        String test2 = "abcd";
        String test3 = "ACDF";
        String test4 = "cscs12AAH";
        String test5 = "SCSCaxax12";
        String test6 = "123SCSCaxax";
        System.out.println(service.checkPassWord(test1));
        System.out.println(service.checkPassWord(test2));
        System.out.println(service.checkPassWord(test3));
        System.out.println(service.checkPassWord(test4));
        System.out.println(service.checkPassWord(test5));
        System.out.println(service.checkPassWord(test6));
    }
}