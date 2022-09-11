package dev.haskin.cookrecipes.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    @JsonInclude(Include.NON_NULL)
    private Long id;

    @NotBlank
    private String username;

    @JsonInclude(Include.NON_NULL)
    Set<RecipeResponse> recipes;
}
