package com.aston.aston_project.api.recipe.client;

import com.aston.aston_project.api.recipe.util.RecipeChecker;
import com.aston.aston_project.api.recipe.util.RecipeCheckerException;
import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import com.aston.aston_project.entity.Recipes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MockRecipeChecker implements RecipeChecker {

    private MockRecipeResponseGenerator generator;
    @Override
    public RecipeCheckerResponse check(Recipes recipes) {
        if(recipes == null){
            throw new RecipeCheckerException("recipe is null");
        }
        return generator.generate(recipes);
    }
}
