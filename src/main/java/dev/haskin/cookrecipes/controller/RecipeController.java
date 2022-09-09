package dev.haskin.cookrecipes.controller;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.haskin.cookrecipes.dto.RecipeResponse;
import dev.haskin.cookrecipes.service.RecipeService;

@RestController
@RequestMapping("/api")
public class RecipeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public Set<RecipeResponse> getRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping("/recipe/{recipeId}")
    public RecipeResponse findRecipeById(@PathVariable Long recipeId) {
        return modelMapper.map(recipeService.findRecipeById(recipeId), RecipeResponse.class);
    }
}
