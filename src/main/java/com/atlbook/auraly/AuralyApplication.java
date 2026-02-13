package com.atlbook.auraly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuralyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuralyApplication.class, args);
    }

}
