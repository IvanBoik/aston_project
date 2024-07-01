package com.aston.aston_project.controller;

import com.aston.aston_project.service.RecipeServiceImpl;
import com.aston.aston_project.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeServiceImpl recipeCRUDService;


    @PostMapping()
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        recipeCRUDService.addRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe).getBody();
    }

    @GetMapping()
    public List<Recipe> allRecipes() {
        List<Recipe> recipes = recipeCRUDService.getAllRecipes();

        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.ok(recipes).getBody();
        } else return (List<Recipe>) ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeCRUDService.getRecipeByID(id);

        return recipe != null ? ResponseEntity.ok(recipe) : ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{link}")
    public Object deleteByLink(@PathVariable String link) {
        recipeCRUDService.deleteRecipeByLink(link);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public Object deleteByID(@PathVariable Long id) {
        recipeCRUDService.deleteRecipeByID(id);

        return ResponseEntity.ok().build();
    }
}
