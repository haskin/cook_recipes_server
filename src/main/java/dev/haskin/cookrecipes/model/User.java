package dev.haskin.cookrecipes.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`user`", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
// @AllArgsConstructor
// @NoArgsConstructor
// @Getter
// @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "created_recipes")
    Set<Recipe> createdRecipes;
}
