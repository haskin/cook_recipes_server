package dev.haskin.cookrecipes;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.haskin.cookrecipes.model.Ingredient;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.service.IngredientService;
import dev.haskin.cookrecipes.service.RecipeService;
import dev.haskin.cookrecipes.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CookrecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookrecipesApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner startup(UserService userService, RecipeService recipeService,
			IngredientService ingredientService) {
		return args -> {
			log.info("---------- STARTUP ----------");
			// User user = User.builder().username("username").password("password").build();
			// user = userService.saveUser(user);
			// Recipe chickenParm = Recipe.builder().name("Chicken Parm").instructions("cook
			// chicken").build();

			// Ingredient chicken = Ingredient.builder().name("chicken").build();
			// chicken = ingredientService.saveIngredient(chicken);

			// Recipe chickenParm = new Recipe("chicken parm", "cook chicken");
			// chickenParm = recipeService.saveRecipe(chickenParm);
			// chickenParm.getIngredients().add(chicken);
			// chickenParm.setOwner(user);
			// user.getRecipesOwned().add(chickenParm);
			// userService.saveUser(user);
			initUsers(userService);
			initRecipe(recipeService);
			initIngredients(ingredientService);
			addRecipesToUser(userService, 1L, List.of(1L));
			addIngredientsToRecipe(recipeService, 1L, List.of(1L, 2L));
		};
	}

	public void initUsers(UserService userService) {
		List<User> users = List.of(
				new User("adrian", "adrian"),
				new User("userone", "userone"));
		userService.saveUsers(users);
	}

	public void initRecipe(RecipeService recipeService) {
		List<Recipe> recipes = List.of(
				new Recipe("Cereal", "Add cereal | Add milk",
						"https://images.unsplash.com/photo-1521483451569-e33803c0330c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1085&q=80"));
		recipeService.saveRecipes(recipes);
	}

	public void initIngredients(IngredientService ingredientService) {
		List<Ingredient> ingredients = List.of(new Ingredient("cereal"), new Ingredient("milk"));
		ingredientService.saveIngredients(ingredients);

	}

	public void addRecipesToUser(UserService userService, Long userId,
			List<Long> recipeIds) {
		for (Long recipeId : recipeIds) {
			userService.addCreatedRecipeToUser(userId, recipeId);
		}
	}

	public void addIngredientsToRecipe(RecipeService recipeService, Long recipeId, List<Long> ingredientIds) {
		for (Long ingredientId : ingredientIds) {
			recipeService.updateRecipeIngredient(recipeId, ingredientId);
		}
	}
}
