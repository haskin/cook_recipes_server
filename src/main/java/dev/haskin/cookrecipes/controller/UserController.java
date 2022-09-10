package dev.haskin.cookrecipes.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.haskin.cookrecipes.dto.RecipeResponse;
import dev.haskin.cookrecipes.dto.UserResponse;
import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.security.UserPrincipal;
import dev.haskin.cookrecipes.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserResponse findUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userService.findUserById(userPrincipal.getId());
        return modelMapper.map(user, UserResponse.class);
    }

    @GetMapping("/user/recipes")
    public Set<RecipeResponse> findUserRecipes(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userService.findUserById(userPrincipal.getId());
        return user.getRecipesOwned().stream().map(recipe -> modelMapper.map(recipe, RecipeResponse.class))
                .collect(Collectors.toSet());
    }

    @GetMapping("/users")
    public Set<User> findUsers() {
        return userService.findUsers();
    }

    @PutMapping("/user/recipe/{recipeId}")
    public User saveRecipe(@PathVariable Long recipeId, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userService.addCreatedRecipeToUser(userPrincipal.getId(), recipeId);
    }
}
