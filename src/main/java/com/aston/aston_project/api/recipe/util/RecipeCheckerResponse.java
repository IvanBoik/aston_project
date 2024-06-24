package com.aston.aston_project.api.recipe.util;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface RecipeCheckerResponse {

    String productName();
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss")
    LocalDateTime expiresAt();

    Boolean isValid();
}
