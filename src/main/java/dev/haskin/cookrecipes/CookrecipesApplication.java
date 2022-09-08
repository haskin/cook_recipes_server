package dev.haskin.cookrecipes;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.model.User;
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
	public CommandLineRunner startup(UserService userService, RecipeService recipeService) {
		return args -> {
			log.info("---------- STARTUP ----------");
			User user = User.builder().username("username").password("password").build();
			user = userService.createUser(user);
			// Recipe chickenParm = Recipe.builder().name("Chicken Parm").instructions("cook
			// chicken").build();
			Recipe chickenParm = new Recipe("chicken parm", "cook chicken");
			chickenParm = recipeService.saveRecipe(chickenParm);
			user.getCreatedRecipes().add(chickenParm);
		};
	}
}
