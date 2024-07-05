package com.aston.aston_project.feign.client;

import com.aston.aston_project.feign.dto.YandexResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for using Yandex API
 * @author K. Zemlyakov
 */
@FeignClient(name = "yandex-search-api")
public interface YandexSearchLocationClient {


    /**
     * Request for get location from address
     * @param location - location center of the search radius (i.e. '50.00,-50.00')
     * @param address - Full address to search location (i.e 'Kutuzovskiy district, 1')
     * @return coordinates of address
     * @author K. Zemlyakov
     */
    @GetMapping
    YandexResponse getLocation(@RequestParam("ll") String location, @RequestParam("text") String address);
}
