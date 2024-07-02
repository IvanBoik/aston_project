package com.aston.aston_project.controller;

import com.aston.aston_project.dto.RecipeDtoFull;
import com.aston.aston_project.service.RecipeServiceImpl;
import com.aston.aston_project.entity.Recipe;
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
    private RecipeServiceImpl recipeService;

    @InjectMocks
    private RecipeController recipeController;


    private final RecipeDtoFull dto = new RecipeDtoFull();
    private final List<RecipeDtoFull> recipesList = new ArrayList<>();


    @Test
    void createRecipe_AddNewRecipe_Success() {

//        given
        RecipeDtoFull expected = dto;

//        when
        Recipe actual = recipeController.createRecipe(dto);

//        then
        assertEquals(expected, actual);

        verify(recipeService, times(1)).addRecipe(dto);
    }

    @Test
    void allRecipes_ReturnListOfAllRecipesOfThreeObjects_Success() {

//        given
        recipesList.add(new RecipeDtoFull());
        recipesList.add(new RecipeDtoFull());
        recipesList.add(new RecipeDtoFull());

        List<RecipeDtoFull> expected = recipesList;

        when(recipeService.getAllRecipes()).thenReturn(recipesList);

//        when
        List<RecipeDtoFull> actual = recipeController.allRecipes();

//        then
        assertEquals(expected, actual);
    }

    @Test
    void getRecipeById_ReturnRecipeById_Success() {

//        given
        Long idRecipe = anyLong();

        RecipeDtoFull expected = dto;

        when(recipeService.getRecipeByID(idRecipe)).thenReturn(dto);

//        when
        RecipeDtoFull actual = recipeController.getRecipeById(idRecipe);

//        then
        assertEquals(expected, actual);
    }
}
