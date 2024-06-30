package com.aston.aston_project.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestConfig {


    @Bean
    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer(){
        return new PostgreSQLContainer<>("postgres:16-alpine");
    }

}
