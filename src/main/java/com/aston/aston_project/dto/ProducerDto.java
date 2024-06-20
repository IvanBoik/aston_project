package com.aston.aston_project.dto;

import com.aston.aston_project.entity.Country;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProducerDto {
    private String name;
    private String country;
}