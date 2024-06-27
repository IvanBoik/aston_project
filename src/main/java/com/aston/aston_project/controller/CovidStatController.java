package com.aston.aston_project.controller;

import com.aston.aston_project.feign.client.CovidFeignClient;
import com.aston.aston_project.feign.dto.CovidResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/covid")
@AllArgsConstructor
public class CovidStatController {
    private final CovidFeignClient feignClient;

    @GetMapping("/info")
    public CovidResponse showStat() {
        return feignClient.readCovidStat();
    }
}