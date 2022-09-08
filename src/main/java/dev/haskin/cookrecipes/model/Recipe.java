package dev.haskin.cookrecipes.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;

import dev.haskin.cookrecipes.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String instructions;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @OneToMany(fetch = FetchType.EAGER)
    Set<Ingredient> ingredients = Collections.emptySet();

    public Recipe(@NotBlank String name, @NotBlank String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public void setName(String name) {
        this.name = StringUtil.toProperCase(name);
    }
}
