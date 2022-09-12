package dev.haskin.cookrecipes.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.haskin.cookrecipes.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    public Set<Recipe> findByNameContainingIgnoreCase(String name);
}
