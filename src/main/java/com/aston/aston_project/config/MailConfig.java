package com.aston.aston_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${mail.password}")
    private String password;

    @Value("${mail.username}")
    private String username;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setPassword(password);
        javaMailSender.setUsername(username);
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
