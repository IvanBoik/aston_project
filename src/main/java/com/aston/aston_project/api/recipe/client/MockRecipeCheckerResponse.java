package com.aston.aston_project.api.recipe.client;

import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import lombok.Getter;

import java.time.LocalDateTime;


public record MockRecipeCheckerResponse(
        String productName,
        LocalDateTime expiresAt,
        Boolean isValid) implements RecipeCheckerResponse {}
