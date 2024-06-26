package com.aston.aston_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderWithProductAndRecipeDTO {
    private Long id;
    private UserResponseDTO user;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss")
    private LocalDateTime time;
    private SimpleProductResponseDTO product;
    private List<RecipeResponseDTO> recipes = new ArrayList<>();
}
