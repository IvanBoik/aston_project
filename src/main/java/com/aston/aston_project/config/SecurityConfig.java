//package com.aston.aston_project.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebMvcConfigurationSupport {
//
//    @Profile("noSecurity")
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.csrf(Customizer.withDefaults()).cors(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(request -> request.anyRequest().permitAll()).build();
//    }
//
//}
