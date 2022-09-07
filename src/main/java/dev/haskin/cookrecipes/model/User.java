package dev.haskin.cookrecipes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.UniqueElements;

@Table(name = "`user`", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @UniqueElements
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
