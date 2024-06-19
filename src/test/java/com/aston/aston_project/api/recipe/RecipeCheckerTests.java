package com.aston.aston_project.api.recipe;

import com.aston.aston_project.api.recipe.client.MockRecipeChecker;
import com.aston.aston_project.api.recipe.client.MockRecipeResponseGenerator;
import com.aston.aston_project.api.recipe.util.RecipeCheckerException;
import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.ProductList;
import com.aston.aston_project.entity.Recipes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.RetryingTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RecipeCheckerTests {

    @Mock
    private Recipes recipe;

    @Mock
    private ProductList productList;

    @Mock
    private Product product;

    private MockRecipeChecker recipeCheckerApi;


    @BeforeEach
    public void init(){
        recipeCheckerApi = new MockRecipeChecker(new MockRecipeResponseGenerator());
    }
    @RetryingTest(2)
    public void recipe_signature_and_product_name_is_correct_test(){
        when(product.getName()).thenReturn("Азитромицин");
        when(productList.getIdProduct()).thenReturn(product);
        when(recipe.getIdProductList()).thenReturn(productList);
        when(recipe.getLink()).thenReturn("00Д1234567890");
        assertThat(recipeCheckerApi.check(recipe).isValid()).isEqualTo(true);
    }

    @Test
    public void recipe_is_null_throw_exception(){
        assertThrows(RecipeCheckerException.class,()->recipeCheckerApi.check(null));
    }

    @Test
    public void recipe_link_signature_is_not_valid(){
        when(product.getName()).thenReturn("Азитромицин");
        when(productList.getIdProduct()).thenReturn(product);
        when(recipe.getIdProductList()).thenReturn(productList);
        when(recipe.getLink()).thenReturn("00-1234567890");
        assertThat(recipeCheckerApi.check(recipe).isValid()).isEqualTo(false);
    }

    @Test
    public void recipe_product_name_is_blank(){
        when(product.getName()).thenReturn("");
        when(productList.getIdProduct()).thenReturn(product);
        when(recipe.getIdProductList()).thenReturn(productList);
        when(recipe.getLink()).thenReturn("00Д1234567890");
        assertThrows(RecipeCheckerException.class,()->recipeCheckerApi.check(recipe));
    }

    @Test
    public void recipe_product_name_is_null(){
        when(product.getName()).thenReturn(null);
        when(productList.getIdProduct()).thenReturn(product);
        when(recipe.getIdProductList()).thenReturn(productList);
        when(recipe.getLink()).thenReturn("00Д1234567890");
        assertThrows(RecipeCheckerException.class,()->recipeCheckerApi.check(recipe));
    }

    @RetryingTest(20)
    public void recipe_product_name_returns_shuffled_with_15_percent(){
        when(product.getName()).thenReturn("Азитромицин");
        when(productList.getIdProduct()).thenReturn(product);
        when(recipe.getIdProductList()).thenReturn(productList);
        when(recipe.getLink()).thenReturn("00Д1234567890");
        RecipeCheckerResponse check = recipeCheckerApi.check(recipe);
        assertThat(check.productName()).isNotEqualTo(product.getName());
    }

    @RetryingTest(20)
    public void recipe_status_generates_negative_with_15_percents(){
        when(product.getName()).thenReturn("Азитромицин");
        when(productList.getIdProduct()).thenReturn(product);
        when(recipe.getIdProductList()).thenReturn(productList);
        when(recipe.getLink()).thenReturn("00Д1234567890");
        assertThat(recipeCheckerApi.check(recipe).isValid()).isEqualTo(false);
    }


}
