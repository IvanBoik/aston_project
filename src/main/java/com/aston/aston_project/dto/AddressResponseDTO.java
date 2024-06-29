package com.aston.aston_project.dto;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private Long id;
    private String city,street,house;
    private short room;
}
