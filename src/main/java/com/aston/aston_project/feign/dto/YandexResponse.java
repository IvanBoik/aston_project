package com.aston.aston_project.feign.dto;

import com.aston.aston_project.feign.mapper.YandexResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 *  Yandex API response view
 * {@link coordinates} - array with length 2, containing latitude and longitude
 */
@Data
@JsonDeserialize(using = YandexResponseDeserializer.class)
public class YandexResponse {
    private Double[] coordinates;
}
