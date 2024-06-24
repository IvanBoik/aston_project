package com.aston.aston_project.dto;

import com.aston.aston_project.entity.ProductList;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderWithRecipeDTO {
    private Long id;
    private UserResponseDTO user;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss")
    private LocalDateTime time;
    private List<SimpleProductResponseDTO> products = new ArrayList<>();
    private List<RecipeResponseDTO> recipes = new ArrayList<>();
}
