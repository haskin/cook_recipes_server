package dev.haskin.cookrecipes.model;

import java.time.LocalDate;
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

@Table
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String instructions;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Ingredient> ingredients;

    public void setName(String name) {
        this.name = StringUtil.toProperCase(name);
    }
}
