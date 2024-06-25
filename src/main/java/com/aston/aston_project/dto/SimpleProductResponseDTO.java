package com.aston.aston_project.dto;

import lombok.Data;

@Data
public class SimpleProductResponseDTO {
    private Long id;
    private String name;
    private Boolean isPrescriptionRequired;
    private Integer count;
}
