package com.aston.aston_project.controller;

import com.aston.aston_project.feign.dto.YandexResponse;
import com.aston.aston_project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coordinates")
@AllArgsConstructor
public class YandexController {
    private UserService userService;
    @GetMapping
    public YandexResponse getCoordinates(
            @RequestParam("location") String location,
            @RequestParam("address")String address){
        return userService.getCoordinates(location,address);
    }
}
