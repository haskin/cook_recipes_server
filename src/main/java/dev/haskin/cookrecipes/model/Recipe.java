package dev.haskin.cookrecipes.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;

import dev.haskin.cookrecipes.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    // @NotBlank
    // private String instructions;

    @NotBlank
    private String image;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @ManyToOne
    private User owner;

    @Builder.Default
    // @OneToMany(fetch = FetchType.EAGER)
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
    private Set<Ingredient> ingredients = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Instruction> instructions = new HashSet<>();

    // public Recipe(@NotBlank String name, @NotBlank String instructions, @NotBlank
    // String image) {
    // this.name = name;
    // this.instructions = instructions;
    // this.image = image;
    // this.ingredients = new HashSet<>();
    // }

    public Recipe(@NotBlank String name, @NotBlank String image, Set<Instruction> instructions) {
        this.name = name;
        this.image = image;
        this.instructions = instructions;
    }

    public Recipe(@NotBlank String name, @NotBlank String image) {
        this.name = name;
        this.image = image;
    }

    public void setName(String name) {
        this.name = StringUtil.toProperCase(name);
    }
}
