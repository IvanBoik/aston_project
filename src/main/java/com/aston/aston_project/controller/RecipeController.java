package com.aston.aston_project.controller;

import com.aston.aston_project.crud.recipe.RecipeCRUDService;
import com.aston.aston_project.entity.Recipes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeCRUDService recipeCRUDService;


    @PostMapping(value = "new_recipe")
    public ResponseEntity<Recipes> createRecipe(@RequestBody Recipes recipe) {
        recipeCRUDService.addRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @GetMapping(value = "/recipes/all")
    public ResponseEntity<List<Recipes>> allRecipes() {
        List<Recipes> recipes = recipeCRUDService.getAllRecipes();

        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.ok(recipes);
        } else return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Recipes> getRecipeById(@PathVariable Long id) {
        Recipes recipe = recipeCRUDService.getRecipeByID(id);

        return recipe != null ? ResponseEntity.ok(recipe) : ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/delete/{link}")
    public ResponseEntity<String> deleteByLink(@PathVariable String link) {
        recipeCRUDService.deleteRecipeByLink(link);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<Long> deleteByID(@PathVariable Long id) {
        recipeCRUDService.deleteRecipeByID(id);

        return ResponseEntity.ok().build();
    }
}
