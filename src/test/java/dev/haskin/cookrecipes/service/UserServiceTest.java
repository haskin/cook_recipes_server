package dev.haskin.cookrecipes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void init() {
        user = new User(USERNAME, PASSWORD);
        user.setId(0L);
        user.setRecipesOwned(new HashSet<>());
    }

    @Test
    void testAddCreatedRecipeToUser() {
        Recipe recipe = new Recipe();
        recipe.setId(0L);
        when(recipeService.findRecipeById(recipe.getId())).thenReturn(recipe);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User actualUser = userService.addCreatedRecipeToUser(user.getId(), recipe.getId());
        assertTrue(actualUser.getRecipesOwned().contains(recipe));
    }

    @Test
    void testDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(0L);
        recipe.setOwner(user);
        when(recipeService.findRecipeById(recipe.getId())).thenReturn(recipe);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User actualUser = userService.deleteRecipe(user.getId(), recipe.getId());
        assertFalse(actualUser.getRecipesOwned().contains(recipe));
    }

    @Test
    void testFindUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(user, userService.findUserById(user.getId()));
    }

    // @Test
    // void testFindUsers() {

    // }

    // @Test
    // void testSaveCreatedRecipe() {

    // }

    @Test
    void testSaveUser() {
        String passwordEncoded = "encoded";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(passwordEncoded);
        when(userRepository.save(user)).thenReturn(user);
        User actualUser = userService.saveUser(user);
        assertEquals(user, actualUser);
        assertEquals(passwordEncoded, actualUser.getPassword());
    }

    // @Test
    // void testSaveUsers() {
    // }
}
