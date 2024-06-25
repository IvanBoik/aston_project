package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
