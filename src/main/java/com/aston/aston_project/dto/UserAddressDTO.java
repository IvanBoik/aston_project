package com.aston.aston_project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserAddressDTO {
    private Long id;
    private UserResponseDTO user;
    private List<AddressResponseDTO> addresses = new ArrayList<>();
}
