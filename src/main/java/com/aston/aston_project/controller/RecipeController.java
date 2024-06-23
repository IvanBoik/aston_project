package com.aston.aston_project.controller;

import com.aston.aston_project.crud.impl.RecipeCRUDServiceImpl;
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

    private final RecipeCRUDServiceImpl recipeCRUDService;


    @PostMapping(value = "/posts/new")
    public ResponseEntity<Recipes> createRecipe(@RequestBody Recipes recipe) {
        recipeCRUDService.addRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @GetMapping(value = "/posts")
    public List<Recipes> allRecipes() {
        List<Recipes> recipes = recipeCRUDService.getAllRecipes();

        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.ok(recipes).getBody();
        } else return (List<Recipes>) ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<Recipes> getRecipeById(@PathVariable Long id) {
        Recipes recipe = recipeCRUDService.getRecipeByID(id);

        return recipe != null ? ResponseEntity.ok(recipe) : ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/posts/{link}")
    public Object deleteByLink(@PathVariable String link) {
        recipeCRUDService.deleteRecipeByLink(link);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/posts/{id}")
    public Object deleteByID(@PathVariable Long id) {
        recipeCRUDService.deleteRecipeByID(id);

        return ResponseEntity.ok().build();
    }
}
