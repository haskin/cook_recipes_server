package dev.haskin.cookrecipes.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.haskin.cookrecipes.model.Instruction;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.repository.InstructionRepository;

@Service
public class InstructionService {

    @Autowired
    private InstructionRepository instructionRepository;

    public Instruction saveInstruction(Instruction instruction) {
        return instructionRepository.save(instruction);
    }

    public Set<Instruction> getInstructionByRecipeId(Long recipeId) {
        return null;
        // return instructionRepository.find
    }

    @Transactional
    public void updateInstructions(Recipe recipe) {
        for (Instruction instruction : recipe.getInstructions()) {
            instruction.setRecipe(recipe);
        }
    }

    @Transactional
    public void deleteInstructions(Set<Instruction> instructions) {
        instructionRepository.deleteAll(instructions);
    }
}
