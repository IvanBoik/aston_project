package com.aston.aston_project.api.recipe.util;

import com.aston.aston_project.entity.Recipe;

public interface RecipeChecker {

    RecipeCheckerResponse check(Recipe recipe);
}
