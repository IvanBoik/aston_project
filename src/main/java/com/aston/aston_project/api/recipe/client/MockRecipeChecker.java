package com.aston.aston_project.api.recipe.client;

import com.aston.aston_project.api.recipe.util.RecipeChecker;
import com.aston.aston_project.api.recipe.util.RecipeCheckerException;
import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import com.aston.aston_project.entity.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Component whose purpose is to simulate recipe checking logic
 * @author K.Zemlyakov
 */
@Component
@AllArgsConstructor
public class MockRecipeChecker implements RecipeChecker {

    private MockRecipeResponseGenerator generator;
    @Override
    public RecipeCheckerResponse check(Recipe recipe) {
        if(recipe == null){
            throw new RecipeCheckerException("recipe is null");
        }
        return generator.generate(recipe);
    }
}
