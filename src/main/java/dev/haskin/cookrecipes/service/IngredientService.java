package dev.haskin.cookrecipes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.haskin.cookrecipes.model.Ingredient;
import dev.haskin.cookrecipes.repository.IngredientRepository;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Optional<Ingredient> getIngredientByName(String name) {
        return ingredientRepository.getIngredientByName(name);
    }
}
