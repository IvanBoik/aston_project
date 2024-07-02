package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.RecipeResponseDTO;
import com.aston.aston_project.dto.SimpleProductDTO;
import com.aston.aston_project.dto.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderWithProductAndRecipeDTO extends OrderWithProductDTO {
    private Long id;
    private UserResponseDTO user;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss")
    private LocalDateTime time;
    private SimpleProductDTO product;
    private List<RecipeResponseDTO> recipes = new ArrayList<>();
}
