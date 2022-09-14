package dev.haskin.cookrecipes.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecipeResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    private Set<InstructionRequest> instructions = new HashSet<>();
    // @NotBlank
    // private String instructions;

    @NotBlank
    private String image;

    @JsonInclude(Include.NON_NULL)
    private Set<IngredientRequest> ingredients = new HashSet<>();
}
