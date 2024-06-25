package com.aston.aston_project.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name, surname, email, phone;
}
