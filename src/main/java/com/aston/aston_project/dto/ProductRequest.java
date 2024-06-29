package com.aston.aston_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
public class ProductRequest {
    private String name;
    private BigDecimal price;
    private Long type;
    private Boolean isPrescriptionRequired;
    private Long producer;
    private Map<Long, Long> attributesValues;
}
