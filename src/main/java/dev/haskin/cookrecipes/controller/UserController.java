package dev.haskin.cookrecipes.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    Set<User> readUsers() {
        return userService.readUsers();
    }
}
