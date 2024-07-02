package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.RecipeResponseDTO;

import java.util.List;

public class OrderWithAddressProductsAndRecipesDTO extends OrderWithAddressAndProductsDTO{
    private List<RecipeResponseDTO> recipes;
}
