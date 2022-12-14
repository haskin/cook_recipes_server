package dev.haskin.cookrecipes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IngredientResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
