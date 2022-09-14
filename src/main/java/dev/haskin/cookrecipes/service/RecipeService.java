package dev.haskin.cookrecipes.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.haskin.cookrecipes.dto.IngredientRequest;
import dev.haskin.cookrecipes.dto.RecipeRequest;
import dev.haskin.cookrecipes.dto.RecipeResponse;
import dev.haskin.cookrecipes.model.Ingredient;
import dev.haskin.cookrecipes.model.Instruction;
import dev.haskin.cookrecipes.model.InstructionService;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.repository.RecipeRepository;
import dev.haskin.cookrecipes.util.StringUtil;

@Service
public class RecipeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InstructionService instructionService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeRepository recipeRepository;

    public Set<RecipeResponse> getRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(recipe -> modelMapper.map(recipe, RecipeResponse.class))
                .collect(Collectors.toSet());
    }

    public Set<RecipeResponse> getRecipesByName(String name) {
        Set<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(name);
        return recipes.stream().map(recipe -> modelMapper.map(recipe, RecipeResponse.class))
                .collect(Collectors.toSet());
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void saveRecipes(List<Recipe> recipes) {
        recipeRepository.saveAll(recipes);
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipe id was not found"));
    }

    @Transactional
    public Recipe updateRecipe(Recipe recipe, RecipeRequest recipeRequest) {
        recipe.setName(recipeRequest.getName());
        recipe.setImage(recipeRequest.getImage());
        Set<Instruction> instructions = recipeRequest.getInstructions().stream()
                .map(instruction -> modelMapper.map(instruction, Instruction.class))
                .map(instruction -> instructionService.saveInstruction(instruction))
                .collect(Collectors.toSet());
        recipe.setInstructions(instructions);
        // recipe.setInstructions(recipeRequest.getInstructions());
        return recipe;
    }

    public void updateRecipeIngredient(Long recipeId, Long ingredientId) {
        Recipe recipe = findRecipeById(recipeId);
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId);
        recipe.getIngredients().add(ingredient);
        recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipeIngredients(Recipe recipe, IngredientRequest[] ingredientRequests) {
        Set<Ingredient> ingredients = new HashSet<>();
        for (IngredientRequest ingredientRequest : ingredientRequests) {
            // Convert to proper name format
            ingredientRequest.setName(StringUtil.toProperCase(ingredientRequest.getName()));
            // Either create a new ingredient or get on in the database already
            Ingredient ingredient = ingredientService.getIngredientByName(ingredientRequest.getName())
                    .orElse(ingredientService.saveIngredient(modelMapper.map(ingredientRequest, Ingredient.class)));
            ingredients.add(ingredient);
        }
        // recipe.getIngredients().addAll(ingredients);
        recipe.setIngredients(ingredients);
        return recipe;
    }

    public void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
}