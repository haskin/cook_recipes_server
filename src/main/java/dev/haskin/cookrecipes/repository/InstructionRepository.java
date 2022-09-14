package dev.haskin.cookrecipes.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.haskin.cookrecipes.model.Instruction;
import dev.haskin.cookrecipes.model.Recipe;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long> {
    Set<Instruction> findByRecipeOrderByStep(Recipe recipe);
}
