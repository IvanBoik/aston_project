package com.aston.aston_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@EnableFeignClients
public class AstonProjectApplication {

    public static final Logger log = LoggerFactory.getLogger(AstonProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AstonProjectApplication.class, args);
        log.info("Приложение запустилось");

    }
}
