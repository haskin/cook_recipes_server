package dev.haskin.cookrecipes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.haskin.cookrecipes.model.Instruction;
import dev.haskin.cookrecipes.model.Recipe;
import dev.haskin.cookrecipes.repository.InstructionRepository;

@ExtendWith(MockitoExtension.class)
public class InstructionServiceTest {

    @Mock
    private InstructionRepository instructionRepository;

    @Mock
    private Recipe recipe;

    @InjectMocks
    private InstructionService instructionService;

    private Instruction instruction;

    @BeforeEach
    void init() {
        instruction = new Instruction(0, "test instruction");
    }

    @Test
    void testSaveInstruction() {
        when(instructionRepository.save(instruction)).thenReturn(instruction);
        assertEquals(instruction, instructionService.saveInstruction(instruction));
    }

    @Test
    void testUpdateInstructions() {
        // final Recipe recipeSpy = Mockito.spy(new Recipe());
        // when(recipeSpy.getInstructions()).thenReturn(Set.of(instruction));
        // assertEquals(recipeSpy, instructionSer.getRecipe());
        when(recipe.getInstructions()).thenReturn(Set.of(instruction));
        instructionService.updateInstructions(recipe);
        assertEquals(recipe, instruction.getRecipe());
    }

    @Test
    void testDeleteInstructions() {
        Set<Instruction> instructions = Set.of(instruction);
        instructionService.deleteInstructions(instructions);
        verify(instructionRepository, times(1)).deleteAll(instructions);
    }

}
