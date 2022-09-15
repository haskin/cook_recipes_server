package dev.haskin.cookrecipes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.haskin.cookrecipes.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    public Optional<Ingredient> getFirstIngredientByName(String name);

}
