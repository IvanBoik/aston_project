package com.aston.aston_project.feign.dto;

import com.aston.aston_project.feign.mapper.YandexResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = YandexResponseDeserializer.class)
public class YandexResponse {
    private Double[] coordinates;
}
