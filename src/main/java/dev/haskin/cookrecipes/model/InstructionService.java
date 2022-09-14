package dev.haskin.cookrecipes.model;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
