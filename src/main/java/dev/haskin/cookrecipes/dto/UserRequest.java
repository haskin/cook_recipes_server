package dev.haskin.cookrecipes.dto;

import javax.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
