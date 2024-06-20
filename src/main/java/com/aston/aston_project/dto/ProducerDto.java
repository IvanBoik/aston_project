package com.aston.aston_project.dto;

import com.aston.aston_project.entity.Country;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerDto {
    private String name;
    private Country country;
}