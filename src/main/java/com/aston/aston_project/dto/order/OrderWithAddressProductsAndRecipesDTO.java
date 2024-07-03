package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.RecipeResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderWithAddressProductsAndRecipesDTO extends OrderWithAddressAndProductsDTO{
    private List<RecipeResponseDTO> recipes;
}
