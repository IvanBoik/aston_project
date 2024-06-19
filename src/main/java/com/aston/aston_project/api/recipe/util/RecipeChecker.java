package com.aston.aston_project.api.recipe.util;

import com.aston.aston_project.entity.Recipes;

public interface RecipeChecker {

    RecipeCheckerResponse check(Recipes recipes);
}
