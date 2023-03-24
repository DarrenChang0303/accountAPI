package com.example.demo.repository;

import com.example.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    boolean existsByName(String username);

    List<UserInfo> findByName(String username);
//    List<UserInfo> findByName(String username);
}
