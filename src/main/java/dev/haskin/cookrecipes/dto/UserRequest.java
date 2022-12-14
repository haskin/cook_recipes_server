package dev.haskin.cookrecipes.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
