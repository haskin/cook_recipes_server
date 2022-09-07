package dev.haskin.cookrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.haskin.cookrecipes.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
