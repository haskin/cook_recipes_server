package dev.haskin.cookrecipes.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecipeRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String instructions;

    @NotBlank
    private String image;
}
