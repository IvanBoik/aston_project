package com.aston.aston_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class AstonProjectApplication {

    private static final Logger log = LoggerFactory.getLogger(AstonProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AstonProjectApplication.class, args);
        log.info("Приложение запустилось");
    }
}
