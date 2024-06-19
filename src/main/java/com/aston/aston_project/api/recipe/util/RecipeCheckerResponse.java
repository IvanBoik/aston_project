package com.aston.aston_project.api.recipe.util;

import java.time.LocalDateTime;

public interface RecipeCheckerResponse {

    String productName();

    LocalDateTime expiresAt();

    Boolean isValid();
}
