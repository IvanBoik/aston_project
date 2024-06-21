package com.aston.aston_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDtoShort {
    private String name;
    private BigDecimal price;
    private String type;
    private Boolean isPrescriptionRequired;
    private ProducerDto producer;
}