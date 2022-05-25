package com.four;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.four.mapper")
public class SpringBootAmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAmsApplication.class, args);
    }

}
