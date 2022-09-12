package dev.haskin.cookrecipes.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.repository.UserRepository;

@Transactional
@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeService recipeService;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void saveUsers(List<User> users) {
        users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        userRepository.saveAll(users);
    }

    public Set<User> findUsers() {
        return userRepository.findAll().stream().collect(Collectors.toSet());
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    @Transactional
    public User saveCreatedRecipe(Long userId, Long recipeId) {
        User user = findUserById(userId);
        Recipe recipe = recipeService.findRecipeById(recipeId);
        user.getRecipesOwned().add(recipe);
        recipe.setOwner(user);
        return userRepository.save(user);
    }

    @Transactional
    public User addCreatedRecipeToUser(Long userId, Long recipeId) {
        User user = findUserById(userId);
        Recipe recipe = recipeService.findRecipeById(recipeId);
        recipe.setOwner(user);
        user.getRecipesOwned().add(recipe);
        return user;
        // return userRepository.save(user);
    }

    @Transactional
    public User deleteRecipe(Long userId, Long recipeId) {
        User user = findUserById(userId);
        Recipe recipe = recipeService.findRecipeById(recipeId);
        if (!user.getId().equals(recipe.getOwner().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must match Recipe owner for deletion ");
        user.getRecipesOwned().remove(recipe);
        recipeService.deleteRecipe(recipe);
        return user;
    }
}
