package com.aston.aston_project.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "covid-client", url = "${feign.client.baseUrl}"
        )
public interface CovidFeignClient {
    @GetMapping("/Russia")
//    ResponseEntity<Void> readCovidStat();
    void readCovidStat();
}