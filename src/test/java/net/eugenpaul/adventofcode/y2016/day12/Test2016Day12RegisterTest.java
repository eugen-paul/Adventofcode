package net.eugenpaul.adventofcode.y2016.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.y2016.day12.instruction.InstructionInc;
import net.eugenpaul.adventofcode.y2016.day12.instruction.InstructionJnz;
import net.eugenpaul.adventofcode.y2016.day12.instruction.InstructionCpy;
import net.eugenpaul.adventofcode.y2016.day12.instruction.InstructionDec;

/**
 * Test2016Day12RegisterTest
 */
class Test2016Day12RegisterTest {

    private Computer computer;

    @BeforeEach
    void init() {
        computer = new Computer(List.of('a', 'b', 'c', 'd'), 1);
    }

    @Test
    void test2016Day12ParseIncOk() {
        InstructionInc instruction = InstructionInc.fromString("inc a");
        assertEquals('a', instruction.getRegister());
    }

    @Test
    void test2016Day12IncDoInstruction() {
        InstructionInc instruction = new InstructionInc('a');

        instruction.doInstruction(computer);

        assertEquals(1, computer.getRegister('a'));
        assertEquals(0, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2016Day12ParseDecOk() {
        InstructionDec instruction = InstructionDec.fromString("dec a");
        assertEquals('a', instruction.getRegister());
    }

    @Test
    void test2016Day12DecDoInstruction() {
        InstructionDec instruction = new InstructionDec('a');

        instruction.doInstruction(computer);

        assertEquals(-1, computer.getRegister('a'));
        assertEquals(0, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2016Day12ParseCpyOk() {
        InstructionCpy instruction = InstructionCpy.fromString("cpy 40 a");
        assertEquals(40, instruction.getValue());
        assertNull(instruction.getFrom());
        assertEquals('a', instruction.getTo());
        
        instruction = InstructionCpy.fromString("cpy a b");
        assertNull(instruction.getValue());
        assertEquals('a', instruction.getFrom());
        assertEquals('b', instruction.getTo());
    }

    @Test
    void test2016Day12CpyDoInstruction() {
        InstructionCpy instruction = new InstructionCpy('a', 'b');

        computer.setRegister('a', 5);
        computer.setRegister('b', 10);
        instruction.doInstruction(computer);

        assertEquals(5, computer.getRegister('a'));
        assertEquals(5, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2016Day12CpyDoInstruction2() {
        InstructionCpy instruction = new InstructionCpy(15, 'b');

        computer.setRegister('a', 5);
        computer.setRegister('b', 10);
        instruction.doInstruction(computer);

        assertEquals(5, computer.getRegister('a'));
        assertEquals(15, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2016Day12ParseJnzOk() {
        InstructionJnz instruction = InstructionJnz.fromString("jnz b 40");
        assertEquals('b', instruction.getCheckRegister());
        assertEquals(40, instruction.getOffset());
    }

    @Test
    void test2016Day12JnzDoInstruction() {
        InstructionJnz instruction = new InstructionJnz('a', 5);

        computer.setRegister('a', 5);
        instruction.doInstruction(computer);

        assertEquals(6, computer.getPosition());
    }

    @Test
    void test2016Day12JnzDoInstructionNoJump() {
        InstructionJnz instruction = new InstructionJnz('a', 5);

        instruction.doInstruction(computer);

        assertEquals(2, computer.getPosition());
    }

}