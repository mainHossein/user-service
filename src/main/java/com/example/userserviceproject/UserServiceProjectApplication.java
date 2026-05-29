package com.example.userserviceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UserServiceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceProjectApplication.class, args);
    }

}
