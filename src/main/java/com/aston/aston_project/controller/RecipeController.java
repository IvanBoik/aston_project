package com.aston.aston_project.controller;

import com.aston.aston_project.dto.RecipeDtoFull;
import com.aston.aston_project.service.RecipeServiceImpl;
import com.aston.aston_project.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeServiceImpl recipeService;


    @PostMapping()
    public Recipe createRecipe(@RequestBody RecipeDtoFull recipe) {
        return recipeService.addRecipe(recipe);
    }

    @GetMapping()
    public List<RecipeDtoFull> allRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping(value = "/{id}")
    public RecipeDtoFull getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeByID(id);
    }

    @DeleteMapping(value = "/{link}")
    public void deleteByLink(@PathVariable String link) {
        recipeService.deleteRecipeByLink(link);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteByID(@PathVariable Long id) {
        recipeService.deleteRecipeByID(id);
    }
}
