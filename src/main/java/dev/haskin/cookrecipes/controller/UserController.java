package dev.haskin.cookrecipes.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.haskin.cookrecipes.dto.RecipeRequest;
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
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setRecipes(null);
        return userResponse;
    }

    @GetMapping("/user/recipes")
    public Set<RecipeResponse> findUserRecipes(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userService.findUserById(userPrincipal.getId());
        return user.getRecipesOwned().stream().map(recipe -> modelMapper.map(recipe, RecipeResponse.class))
                .collect(Collectors.toSet());
    }

    // @GetMapping("/users")
    // public Set<User> findUsers() {
    // return userService.findUsers();
    // }

    @PutMapping("/user/recipe/{recipeId}")
    public UserResponse saveRecipe(@PathVariable Long recipeId, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return modelMapper.map(userService.addCreatedRecipeToUser(userPrincipal.getId(), recipeId), UserResponse.class);
    }

    @DeleteMapping("user/recipe/{recipeId}")
    public UserResponse deleteRecipe(@PathVariable Long recipeId, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return modelMapper.map(userService.deleteRecipe(userPrincipal.getId(), recipeId),
                UserResponse.class);
    }

    // @DeleteMapping("user/recipe")
    // public UserResponse deleteRecipe(@Valid @RequestBody RecipeRequest
    // recipeRequest, Authentication authentication) {
    // UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    // if (recipeRequest.getId() == null)
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
    // "Recipe data must contain id for delete operation");
    // return modelMapper.map(userService.deleteRecipe(userPrincipal.getId(),
    // recipeRequest.getId()),
    // UserResponse.class);
    // }
}
