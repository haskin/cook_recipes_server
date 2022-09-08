package dev.haskin.cookrecipes.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;

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

    public void setName(String name) {
        // this.name = name.trim().to;
    }
}
