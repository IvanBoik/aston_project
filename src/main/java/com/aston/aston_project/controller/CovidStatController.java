package com.aston.aston_project.controller;

import com.aston.aston_project.feign.CovidFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/covid")
@AllArgsConstructor
public class CovidStatController {
private final CovidFeignClient feignClient;

    @GetMapping("/Russia")
    public void showStat(){
        feignClient.readCovidStat();
    }
//    public ResponseEntity showStat(){
//        return ResponseEntity.ok(feignClient.readCovidStat());
//    }
}