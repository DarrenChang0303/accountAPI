package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyServiceTest {
    @Autowired
    MyService myService;
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