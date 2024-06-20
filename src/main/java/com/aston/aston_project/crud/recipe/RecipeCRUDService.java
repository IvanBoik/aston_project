package com.aston.aston_project.crud.recipe;

import com.aston.aston_project.entity.Recipes;

import java.util.List;

public interface RecipeCRUDService {

    Recipes addRecipe(Recipes recipe);
    List<Recipes> getAllRecipes();
    Recipes getRecipeByID(long id);
    Recipes deleteRecipeByLink(String link);
    void deleteRecipeByID(long id);
}
