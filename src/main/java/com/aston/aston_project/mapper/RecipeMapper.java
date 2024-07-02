package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.RecipeDtoFull;
import com.aston.aston_project.dto.RecipeResponseDTO;
import com.aston.aston_project.entity.Recipe;
import org.mapstruct.Mapper;

@Mapper
public interface RecipeMapper {

    RecipeResponseDTO toDto(Recipe recipe);
    RecipeDtoFull toDtoFull(Recipe recipe);
    Recipe toEntity(RecipeDtoFull dto);
}
