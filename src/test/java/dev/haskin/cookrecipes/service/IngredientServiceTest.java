package dev.haskin.cookrecipes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.haskin.cookrecipes.model.Ingredient;
import dev.haskin.cookrecipes.repository.IngredientRepository;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    private Ingredient ingredient;

    @BeforeEach
    public void init() {
        ingredient = new Ingredient(0, "ingredient");
    }

    @Test
    void testSaveIngredient() {
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        assertEquals(ingredient, ingredientService.saveIngredient(ingredient));
    }

    @Test
    void testSaveIngredients() {
        List<Ingredient> ingredients = List.of(ingredient);
        when(ingredientRepository.saveAll(ingredients)).thenReturn(ingredients);
        assertEquals(ingredients, ingredientService.saveIngredients(ingredients));
    }

    @Test
    void testFindIngredientById() {
        when(ingredientRepository.findById(ingredient.getId())).thenReturn(Optional.of(ingredient));
        assertEquals(ingredient, ingredientService.findIngredientById(ingredient.getId()));
    }

    @Test
    void testGetIngredientByName() {

        when(ingredientRepository.getFirstIngredientByName(ingredient.getName())).thenReturn(Optional.of(ingredient));
        assertEquals(ingredient, ingredientService.getIngredientByName(ingredient.getName()).get());
    }

}
