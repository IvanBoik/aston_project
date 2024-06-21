package com.aston.aston_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDtoFull {

    private String name;
    private BigDecimal price;
    private String type;
    private Boolean isPrescriptionRequired;
    private ProducerDto producer;
    private Map<String, String> attributesValues = new HashMap<>();

    public void setAttributesValues(String key, String value) {
        this.attributesValues.put(key, value);
    }
}