package com.example.websitedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.websitedemo.map")
public class WebsiteDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteDemoApplication.class, args);
    }

}
