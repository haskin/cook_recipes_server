package dev.haskin.cookrecipes.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dev.haskin.cookrecipes.model.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecipeRequest {
    @JsonInclude(Include.NON_NULL)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String instructions;

    @NotBlank
    private String image;

    @JsonInclude(Include.NON_NULL)
    private Set<IngredientRequest> ingredients = new HashSet<>();
}
