package com.aston.aston_project.crud.recipe.impl;

import com.aston.aston_project.crud.recipe.RecipeCRUDService;
import com.aston.aston_project.entity.Recipes;
import com.aston.aston_project.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeCRUDServiceImpl implements RecipeCRUDService {

    private final RecipeRepository recipeRepository;

    @Override
    public Recipes addRecipe(Recipes recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipes> getAllRecipes() {
        Optional<List <Recipes>> recipes = Optional.of(recipeRepository.findAll());
        return recipes
                .orElseThrow(() -> {
            new NullPointerException("No one recipe found");
            return null;
        });
    }

    @Override
    public Recipes getRecipeByID(long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->{
                    new NullPointerException("No one recipe found");
                    return null;
                });
    }

    @Override
    public void updateRecipeById(long id, Recipes recipe) {
        Optional<Recipes> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipes existingRecipe = optionalRecipe.get();
            existingRecipe.setLink(recipe.getLink());
            existingRecipe.setIdProductList(recipe.getIdProductList());
            recipeRepository.save(existingRecipe);
        } else {
            throw new RuntimeException("Recipe not found with id " + id);
        }
    }

    @Override
    public Recipes deleteRecipeByLink(String link) {
        Optional<Recipes> recipesOptional = Optional.ofNullable(recipeRepository.findByLink(link));

        if (recipesOptional.isEmpty()) throw new NullPointerException(String.format("No recipe found for name %s", link));

        return recipeRepository.deleteByLink(link);
        }

    @Override
    public void deleteRecipeByID(long id) {
        if (recipeRepository.findById(id).isEmpty()) throw new NullPointerException(String.format("No recipe found for name %s", id));

        recipeRepository.deleteById(id);
    }
}
