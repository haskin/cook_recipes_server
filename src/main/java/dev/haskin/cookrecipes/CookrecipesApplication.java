package dev.haskin.cookrecipes;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.haskin.cookrecipes.model.Ingredient;
import dev.haskin.cookrecipes.model.Instruction;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.service.IngredientService;
import dev.haskin.cookrecipes.service.InstructionService;
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
			IngredientService ingredientService, InstructionService instructionService) {
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
			initRecipe(recipeService, instructionService);
			initIngredients(ingredientService);
			addRecipesToUser(userService, 1L, List.of(1L, 2L, 3L, 4L, 5L, 6L));
			// addIngredientsToRecipe(recipeService, 1L, List.of(1L, 2L));
		};
	}

	public void initUsers(UserService userService) {
		List<User> users = List.of(
				new User("adrian", "adrian"),
				new User("userone", "userone"));
		// userService.saveUsers(users);
		users.forEach(user -> userService.saveUser(user));
	}

	@Transactional
	public void initRecipe(RecipeService recipeService, InstructionService instructionService) {
		Set<Instruction> cerealInstructions = Set.of(new Instruction(0, "put cereal in bowl"),
				new Instruction(1, "put milk in bowl"));
		// List<Recipe> recipes = List.of(
		// new Recipe("Cereal", "Add cereal | Add milk",
		// "https://images.unsplash.com/photo-1521483451569-e33803c0330c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1085&q=80"));

		List<Recipe> recipes = List.of(
				new Recipe("Cereal",
						"https://images.unsplash.com/photo-1521483451569-e33803c0330c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1085&q=80",
						cerealInstructions));
		Recipe recipe = new Recipe("Cereal",
				"https://images.unsplash.com/photo-1521483451569-e33803c0330c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1085&q=80",
				cerealInstructions);
		recipe = recipeService.saveRecipe(recipe);
		for (Instruction instruction : cerealInstructions) {
			instruction.setRecipe(recipe);
			instructionService.saveInstruction(instruction);
		}

		List<Set<Instruction>> allInstructions = List.of(
				Set.of(
						new Instruction(0, "Preheat oven to 350 degrees F (175 degrees C)."),
						new Instruction(1,
								"In a medium saucepan over medium heat, blend cider vinegar, chili sauce, Worcestershire sauce, and tomato paste. Mix in the onion, brown sugar, and cayenne pepper."),
						new Instruction(2,
								"Heat oil in a medium skillet over medium heat, and saute the chicken thighs until browned. Remove from heat, drain, and arrange in a medium baking dish. Cover with the cider vinegar sauce mixture."),
						new Instruction(3,
								"Bake covered 45 minutes in the preheated oven, or until chicken is no longer pink and juices run clear.")),
				Set.of(
						new Instruction(0, "Preheat the oven to 350 degrees F (175 degrees C)."),
						new Instruction(1,
								"Stir together cream of chicken soup, cottage cheese, sour cream, cream cheese, Creole seasoning, onion powder, and 1/2 teaspoon of the garlic powder in a medium bowl until well blended and smooth."),
						new Instruction(3,
								"Stir together crackers, melted butter, and remaining 1/2 teaspoon garlic powder in a medium bowl.")),

				Set.of(
						new Instruction(0, "Preheat the oven to 425 degrees F (220 degrees C."),
						new Instruction(1,
								"Combine chicken, carrots, peas, and celery in a saucepan; add water to cover and bring to a boil. Boil for 15 minutes, then remove from the heat and drain."),
						new Instruction(2,
								"While the chicken is cooking, melt butter in another saucepan over medium heat. Add onion and cook until soft and translucent, 5 to 7 minutes. Stir in flour, salt, pepper, and celery seed"),
						new Instruction(3,
								"Place chicken and vegetables in the bottom pie crust. Pour hot liquid mixture over top. Cover with top crust, seal the edges, and cut away any excess dough."),
						new Instruction(4,
								"Bake in the preheated oven until pastry is golden brown and filling is bubbly, 30 to 35 minutes. Cool for 10 minutes before serving.")),
				Set.of(
						new Instruction(0,
								"Place almonds in a frying pan. Toast over medium-high heat, shaking frequently. Watch carefully, as they burn easily."),
						new Instruction(1,
								"Mix together mayonnaise, lemon juice, and pepper in a medium bowl. Toss with chicken, toasted almonds, and celery.")),

				Set.of(
						new Instruction(0,
								"Bring a large pot of lightly salted water to a boil. Add pasta and cook until al dente, 8 to 10 minutes. Drain."),
						new Instruction(1,
								"Cut chicken breast into strips. Place chicken and Cajun seasoning in a plastic bag. Shake to coat."),
						new Instruction(2,
								"Melt butter in a large skillet over medium heat. Add chicken and cook, stirring, until browned and almost cooked through, 5 to 7 minutes."),
						new Instruction(3,
								"Add bell peppers, mushrooms, and green onion. Cook, stirring, 2 to 3 minutes."),
						new Instruction(4,
								"Reduce the heat and stir in cream, basil, lemon pepper, salt, garlic powder, and black pepper. Heat through. Add cooked linguine, toss, and heat through."),
						new Instruction(5, "Sprinkle with Parmesan and serve.")

				));
		List<Recipe> allRecipes = List.of(
				new Recipe("Michael's Chicken",
						"https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimages.media-allrecipes.com%2Fuserphotos%2F663489.jpg&w=595&h=595&c=sc&poi=face&q=60&orient=true"),

				new Recipe("Million Dollar Chicken Casserole",
						"https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F43%2F2022%2F08%2F16%2FMillionDollarChickenCasserole-ddmfs-1X1-2911.jpg&w=272&h=272&c=sc&poi=face&q=60&orient=true"),

				new Recipe("Chicken Pot Pie",
						"https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F43%2F2022%2F03%2F09%2F26317-chicken-pot-pie-mfs-481.jpg&w=272&h=272&c=sc&poi=%5B1120%2C1020%5D&q=60&orient=true"),
				new Recipe("Basic Chicken Salad",
						"https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimages.media-allrecipes.com%2Fuserphotos%2F1105316.jpg&w=272&h=272&c=sc&poi=face&q=60&orient=true"),
				new Recipe("Cajun Chicken Pasta",
						"https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F43%2F2022%2F04%2F26%2F8778-cajun-chicken-pasta-humblepieliving-002-1x1-1.jpg&w=272&h=272&c=sc&poi=face&q=60&orient=true"));

		for (int i = 0; i < allRecipes.size(); i++) {
			recipe = allRecipes.get(i);
			Set<Instruction> instructions = allInstructions.get(i);
			recipe.setInstructions(instructions);
			recipe.setIngredients(Set.of(new Ingredient("Chicken")));
			recipe = recipeService.saveRecipe(recipe);
			for (Instruction instruction : instructions) {
				instruction.setRecipe(recipe);
				instructionService.saveInstruction(instruction);
			}
		}
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
