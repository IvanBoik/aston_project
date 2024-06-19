package com.aston.aston_project.api.recipe.client;

import com.aston.aston_project.api.recipe.util.RecipeCheckerException;
import com.aston.aston_project.entity.Recipes;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MockRecipeResponseGenerator {
    private static final String RECIPE_REGEX = "[0-9]{2}[А-я]{1}[0-9]{10}";

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public MockRecipeCheckerResponse generate(Recipes recipes){
        return new MockRecipeCheckerResponse(
                generateProductName(recipes),
                generateExpiresAt(),
                generateRecipeStatus(recipes)
        );
    }

    private boolean generateRecipeStatus(Recipes recipes) {
        String productName = recipes.getIdProductList().getIdProduct().getName();
        boolean linkIsValid = validateRecipeLink(recipes.getLink());
        if(linkIsValid){
            if(productName == null || productName.isBlank()){
                throw new RecipeCheckerException("product name of recipe is null or blank");
            }
            return RANDOM.nextInt(100) >= 15;
        }
        return false;
    }

    private LocalDateTime generateExpiresAt() {
        return LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(RANDOM.nextLong(-25,25));
    }


    private boolean validateRecipeLink(String recipeLink) {
        return recipeLink.matches(RECIPE_REGEX);
    }

    private String generateProductName(Recipes recipes){
        String productName = recipes.getIdProductList().getIdProduct().getName();
        if(productName==null){
            throw new RecipeCheckerException("Product name is null");
        }
        int condition = RANDOM.nextInt(100);
        if(condition < 15){
            return stringShuffle(productName);
        }
        return productName;
    }

    private String stringShuffle(String input){
        char[] charArray = input.toCharArray();
        for (int i = charArray.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = charArray[i];
            charArray[i] = charArray[j];
            charArray[j] = temp;
        }
        return String.valueOf(charArray);
    }

}
