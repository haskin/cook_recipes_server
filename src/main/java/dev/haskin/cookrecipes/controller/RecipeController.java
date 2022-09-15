package dev.haskin.cookrecipes.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.haskin.cookrecipes.dto.IngredientRequest;
import dev.haskin.cookrecipes.dto.IngredientResponse;
import dev.haskin.cookrecipes.dto.RecipeRequest;
import dev.haskin.cookrecipes.dto.RecipeResponse;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.security.UserPrincipal;
import dev.haskin.cookrecipes.service.InstructionService;
import dev.haskin.cookrecipes.service.RecipeService;
import dev.haskin.cookrecipes.service.UserService;

@RestController
@RequestMapping("/api")
public class RecipeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstructionService instructionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public Set<RecipeResponse> getRecipes(@RequestParam(required = false) String name) {
        if (name != null)
            return recipeService.getRecipesByName(name);
        return recipeService.getRecipes();
    }

    // @GetMapping("/recipes")
    // public Set<RecipeResponse> getRecipesByName(@RequestParam String name) {
    // return recipeService.getRecipesByName(name);
    // }

    @GetMapping("/recipe/{recipeId}")
    public RecipeResponse findRecipeById(@PathVariable Long recipeId) {
        return modelMapper.map(recipeService.findRecipeById(recipeId), RecipeResponse.class);
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public Set<IngredientResponse> getRecipeIngredients(@PathVariable Long recipeId) {
        Recipe recipe = recipeService.findRecipeById(recipeId);
        return recipe.getIngredients().stream().map(ingredient -> modelMapper.map(ingredient, IngredientResponse.class))
                .collect(Collectors.toSet());
    }

    @PostMapping("/recipe")
    public RecipeResponse saveRecipe(@Valid @RequestBody RecipeRequest recipeRequest, Authentication authentication) {
        Recipe recipe = modelMapper.map(recipeRequest, Recipe.class);
        instructionService.updateInstructions(recipe);
        return modelMapper.map(recipeService.saveRecipe(recipe), RecipeResponse.class);
    }

    @PutMapping("/recipe/{recipeId}")
    public RecipeResponse updateRecipe(@PathVariable Long recipeId, @Valid @RequestBody RecipeRequest recipeRequest,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Recipe recipe = recipeService.findRecipeById(recipeId);
        if (!userPrincipal.getId().equals(recipe.getOwner().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not own the recipe");
        instructionService.deleteInstructions(recipe.getInstructions());
        recipe = recipeService.updateRecipe(recipe, recipeRequest);
        // If the recipe request have ingredients
        instructionService.updateInstructions(recipe);
        if (recipeRequest.getIngredients() != null) {
            recipeService.updateRecipeIngredients(recipe,
                    recipeRequest.getIngredients().toArray(IngredientRequest[]::new));
        }
        return modelMapper.map(recipe, RecipeResponse.class);
    }

    @PutMapping("/recipe/{recipeId}/ingredients")
    public RecipeResponse saveIngredientsToRecipe(@PathVariable Long recipeId,
            @Valid @RequestBody IngredientRequest[] ingredients,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Recipe recipe = recipeService.findRecipeById(recipeId);
        if (!userPrincipal.getId().equals(recipe.getOwner().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not own the recipe");
        return modelMapper.map(recipeService.updateRecipeIngredients(recipe, ingredients), RecipeResponse.class);
    }

    // @PostMapping("/recipe/{recipeId}/ingredient")
    // public
}
