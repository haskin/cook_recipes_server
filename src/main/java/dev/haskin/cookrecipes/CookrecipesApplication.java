package dev.haskin.cookrecipes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CookrecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookrecipesApplication.class, args);
	}

	@Bean
	public CommandLineRunner startup(UserService userService) {
		return args -> {
			log.info("---------- STARTUP ----------");
			User user = User.builder().username("username").password("password").build();
			userService.createUser(user);
		};
	}
}
