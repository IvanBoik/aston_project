package com.aston.aston_project.repository.recipe;

import com.aston.aston_project.entity.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RecipeRepository extends JpaRepository<Recipes, Long> {

    Recipes findByLink (String link);
    Recipes deleteByLink (String link);
}
