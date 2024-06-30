package com.aston.aston_project.feign.client;

import com.aston.aston_project.feign.dto.YandexResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "yandex-search-api")
public interface YandexSearchLocationClient {


    @GetMapping
    YandexResponse getLocation(@RequestParam("ll") String location, @RequestParam("text") String address);
}
