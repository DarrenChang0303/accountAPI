package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyServiceTest {
    @Autowired
    MyService myService;

    @Test
    public void testCheckPassword(){
        String name = "Test";
        String test1 = "1234";
        String test2 = "abcd";
        String test3 = "ACDF";
        String test4 = "c6A12343211H";
        String test5 = "SCSCaxax12";
        String test6 = "123SCSCaxax";
        System.out.println(myService.checkPassWord(name,test1));
        System.out.println(myService.checkPassWord(name,test2));
        System.out.println(myService.checkPassWord(name,test3));
        System.out.println(myService.checkPassWord(name,test4));
        System.out.println(myService.checkPassWord(name,test5));
        System.out.println(myService.checkPassWord(name,test6));
    }

    @Test
    public void testAccountJpa(){
        System.out.println(myService.accountListJPA());
    }

    @Test
    public void testCreate(){
        myService.accountCreateJPA("JpaTest","JpaPass");
        System.out.println(myService.accountListJPA());
    }
}