package dev.haskin.cookrecipes.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.haskin.cookrecipes.dto.RecipeResponse;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.repository.RecipeRepository;

@Service
public class RecipeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RecipeRepository recipeRepository;

    public Set<RecipeResponse> getRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(recipe -> modelMapper.map(recipe, RecipeResponse.class))
                .collect(Collectors.toSet());
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

}
