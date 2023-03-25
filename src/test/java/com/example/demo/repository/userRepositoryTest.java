package com.example.demo.repository;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.MyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class userRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyService myService;
    @Test
    public void testFindName() {
        UserInfo userInfo = new UserInfo("JpaTest","JpaPassword");
        userRepository.save(userInfo);
        List<UserInfo> userList = userRepository.findAll();
        Assertions.assertEquals(1, userList.size());
        UserInfo savedUser = userList.get(0);
        Assertions.assertEquals("JpaTest", savedUser.getName());
        Assertions.assertEquals("JpaPassword", savedUser.getPassword());
    }
    @Test
    public void  testFindByName(){
        System.out.println(userRepository.existsByName("JpaTest"));
        System.out.println(userRepository.existsByName("Jpa"));
    }
}