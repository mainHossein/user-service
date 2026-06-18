package com.example.userserviceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@EnableCaching
@SpringBootApplication
public class UserServiceProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UserServiceProjectApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        Integer port = environment.getProperty("server.port", Integer.class);
        String path = environment.getProperty("server.servlet.context-path");
        printServerInfo(port, path);
    }

    private static void printServerInfo(Integer port, String path) {
        System.out.printf("""
                --------------------------------------------------
                | Server URL: http://localhost:%d              |
                | Swagger URL: http://localhost:%d%s/swagger-ui/index.html  |
                --------------------------------------------------
                %n""", port, port, path);
    }

}
