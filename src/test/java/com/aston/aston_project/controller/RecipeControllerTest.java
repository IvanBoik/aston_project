package com.aston.aston_project.controller;

import com.aston.aston_project.crud.recipe.RecipeCRUDService;
import com.aston.aston_project.entity.Recipes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class RecipeControllerTest extends com.aston.aston_project.Mock{

    @Mock
    private RecipeCRUDService recipeCRUDService;

    @InjectMocks
    private RecipeController recipeController;


    private final Recipes recipe = new Recipes();
    private final List<Recipes> recipesList = new ArrayList<>();


    @Test
    void createRecipe_AddNewRecipe_Success() {

//        given
        ResponseEntity<Recipes> expected = new ResponseEntity<>(recipe, HttpStatus.CREATED);

//        when
        ResponseEntity<Recipes> actual = recipeController.createRecipe(recipe);

//        then
        assertEquals(expected, actual);

        verify(recipeCRUDService, times(1)).addRecipe(recipe);
    }

    @Test
    void allRecipes_ReturnListOfAllRecipesOfThreeObjects_Success() {

//        given
        recipesList.add(new Recipes());
        recipesList.add(new Recipes());
        recipesList.add(new Recipes());

        ResponseEntity<List<Recipes>> expected = new ResponseEntity<>(recipesList, HttpStatus.OK);

        when(recipeCRUDService.getAllRecipes()).thenReturn(recipesList);

//        when
        ResponseEntity<List<Recipes>> actual = recipeController.allRecipes();

//        then
        assertEquals(expected, actual);
    }

    @Test
    void getRecipeById_ReturnRecipeById_Success() {

//        given
        Long idRecipe = anyLong();
        recipe.setId(idRecipe);

        ResponseEntity<Recipes> expected = new ResponseEntity<>(recipe, HttpStatus.OK);

        when(recipeCRUDService.getRecipeByID(idRecipe)).thenReturn(recipe);

//        when
        ResponseEntity<Recipes> actual = recipeController.getRecipeById(idRecipe);

//        then
        assertEquals(expected, actual);
    }

    @Test
    void deleteByLink_DeleteSRecipe_Success() {

//        given
        String linkRecipe = anyString();
        recipe.setLink(linkRecipe);

        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);

//        when
        ResponseEntity<String> actual = recipeController.deleteByLink(anyString());

//        then
        assertEquals(expected, actual);
    }

    @Test
    void deleteByID_eDeleteRecipe_Success() {

//        given
        recipe.setId(1L);

//        when
        when(recipeCRUDService.getRecipeByID(1L)).thenReturn(recipe);
        doNothing().when(recipeCRUDService).deleteRecipeByID(any());

        ResponseEntity<Long> response = recipeController.deleteByID(1L);

//        then
        verify(recipeCRUDService, times(1)).getRecipeByID(1L);
        verify(recipeCRUDService, times(1)).deleteRecipeByID(any());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
