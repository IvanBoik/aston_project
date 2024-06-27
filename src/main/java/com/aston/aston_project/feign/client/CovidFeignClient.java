package com.aston.aston_project.feign.client;

import com.aston.aston_project.feign.dto.CovidResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "covid-client")
public interface CovidFeignClient {
    @GetMapping("/Russia")
    CovidResponse readCovidStat();
}