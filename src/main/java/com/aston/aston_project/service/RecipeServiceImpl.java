package com.aston.aston_project.service;

import com.aston.aston_project.dto.RecipeDtoFull;
import com.aston.aston_project.entity.Recipe;
import com.aston.aston_project.mapper.RecipeMapper;
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
    private final RecipeMapper recipeMapper;


    public Recipe addRecipe(RecipeDtoFull recipe) {
        return recipeRepository.save(recipeMapper.toEntity(recipe));
    }

    public List<RecipeDtoFull> getAllRecipes() {
        return recipeRepository.findAll().stream().map(recipeMapper::toDtoFull).toList();
    }

    public RecipeDtoFull getRecipeByID(long id) {
        return recipeMapper.toDtoFull(recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Recipe with id " + id + " not found")));
    }

    public void updateRecipeById(long id, Recipe recipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setLink(recipe.getLink());
            existingRecipe.setId(recipe.getId());
            recipeRepository.save(existingRecipe);
        } else {
            throw new NotFoundDataException("Recipe with id " + id + " not found");
        }
    }

    public Recipe deleteRecipeByLink(String link) {
        return recipeRepository.deleteByLink(link);
        }

    public void deleteRecipeByID(long id) {
        recipeRepository.deleteById(id);
    }
}
