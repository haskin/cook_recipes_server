package dev.haskin.cookrecipes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import dev.haskin.cookrecipes.dto.IngredientRequest;
import dev.haskin.cookrecipes.dto.RecipeRequest;
import dev.haskin.cookrecipes.dto.RecipeResponse;
import dev.haskin.cookrecipes.model.Ingredient;
import dev.haskin.cookrecipes.model.Instruction;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.repository.RecipeRepository;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private InstructionService instructionService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe;

    @BeforeEach
    private void init() {
        recipe = new Recipe();
        recipe.setId(0L);
        recipe.setName("name");
    }

    @Test
    void testDeleteRecipe() {
        recipeService.deleteRecipe(recipe);
        verify(recipeRepository, times(1)).delete(recipe);
    }

    @Test
    void testFindRecipeById() {
        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
        assertEquals(recipe, recipeService.findRecipeById(recipe.getId()));
    }

    @Test
    void testGetRecipes() {
        List<Recipe> recipes = List.of(recipe);
        RecipeResponse recipeResponse = new RecipeResponse();
        when(recipeRepository.findAll()).thenReturn(recipes);
        when(modelMapper.map(recipe, RecipeResponse.class)).thenReturn(recipeResponse);
        assertEquals(Set.of(recipeResponse), recipeService.getRecipes());
    }

    @Test
    void testGetRecipesByName() {
        Set<Recipe> recipes = Set.of(recipe);
        RecipeResponse recipeResponse = new RecipeResponse();
        when(recipeRepository.findByNameContainingIgnoreCase(recipe.getName())).thenReturn(recipes);
        when(modelMapper.map(recipe, RecipeResponse.class)).thenReturn(recipeResponse);
        assertEquals(Set.of(recipeResponse), recipeService.getRecipesByName(recipe.getName()));
    }

    @Test
    void testSaveRecipe() {
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        assertEquals(recipe, recipeService.saveRecipe(recipe));
    }

    // @Test
    // void testSaveRecipes() {

    // }

    @Test
    void testUpdateRecipe() {
        Instruction instruction = new Instruction();
        Set<Instruction> instructions = Set.of(instruction);
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("name");
        recipeRequest.setImage("image");
        recipe.setInstructions(instructions);
        // when(modelMapper.map(instruction,
        // Instruction.class)).thenReturn(instruction);
        // when(instructionService.saveInstruction(instruction)).thenReturn(instruction);
        assertEquals(recipe, recipeService.updateRecipe(recipe, recipeRequest));
    }

    @Test
    void testUpdateRecipeIngredient() {
        Ingredient ingredient = new Ingredient("name");
        ingredient.setId(0L);
        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
        when(ingredientService.findIngredientById(ingredient.getId())).thenReturn(ingredient);
        recipeService.updateRecipeIngredient(recipe.getId(), ingredient.getId());
        assertTrue(recipe.getIngredients().contains(ingredient));
    }

    @Test
    void testUpdateRecipeIngredients() {
        Ingredient ingredient = new Ingredient("name");
        IngredientRequest ingredientRequest = new IngredientRequest("name");
        IngredientRequest[] ingredientRequests = { ingredientRequest };
        lenient().when(ingredientService.getIngredientByName(ingredientRequest.getName()))
                .thenReturn(Optional.of(ingredient));
        recipeService.updateRecipeIngredients(recipe, ingredientRequests);
        verify(ingredientService, times(1)).getIngredientByName(ingredientRequest.getName());
    }
}
