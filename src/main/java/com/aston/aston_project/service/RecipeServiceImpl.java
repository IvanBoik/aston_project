package com.aston.aston_project.service;

import com.aston.aston_project.entity.Recipe;
import com.aston.aston_project.repository.RecipeRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl {

    private final RecipeRepository recipeRepository;


    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        List <Recipe> recipes = recipeRepository.findAll();
        return recipes;
    }

    public Recipe getRecipeByID(long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->{
                    new NotFoundDataException("No one recipe found");
                    return null;
                });
    }

    public void updateRecipeById(long id, Recipe recipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setLink(recipe.getLink());
            existingRecipe.setId(recipe.getId());
            recipeRepository.save(existingRecipe);
        } else {
            throw new NotFoundDataException("Recipe not found with id " + id);
        }
    }

    public Recipe deleteRecipeByLink(String link) {
        return recipeRepository.deleteByLink(link);
        }

    public void deleteRecipeByID(long id) {
        recipeRepository.deleteById(id);
    }
}
