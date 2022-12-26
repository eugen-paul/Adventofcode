package net.eugenpaul.adventofcode.y2015.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionHlf;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionInc;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJie;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJio;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJmp;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionTpl;

/**
 * Test2015Day23RegisterTest
 */
class Test2015Day23RegisterTest {

    private Computer computer;

    @BeforeEach
    void init() {
        computer = new Computer(List.of('a', 'b'), 1);
        computer.setRegister('a', 10);
        computer.setRegister('b', 20);
    }

    @Test
    void test2015Day23ParseHlfOk() {
        InstructionHlf instruction = InstructionHlf.fromString("hlf a");
        assertEquals('a', instruction.getRegister());
    }

    @Test
    void test2015Day23HlfDoInstruction() {
        InstructionHlf instruction = new InstructionHlf('a');

        instruction.doInstruction(computer);

        assertEquals(5, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2015Day23ParseTplOk() {
        InstructionTpl instruction = InstructionTpl.fromString("tpl a");
        assertEquals('a', instruction.getRegister());
    }

    @Test
    void test2015Day23TplDoInstruction() {
        InstructionTpl instruction = new InstructionTpl('a');

        instruction.doInstruction(computer);

        assertEquals(30, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2015Day23ParseIncOk() {
        InstructionInc instruction = InstructionInc.fromString("inc a");
        assertEquals('a', instruction.getRegister());
    }

    @Test
    void test2015Day23IncDoInstruction() {
        InstructionInc instruction = new InstructionInc('a');

        instruction.doInstruction(computer);

        assertEquals(11, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2015Day23ParseJmpOk() {
        InstructionJmp instruction = InstructionJmp.fromString("jmp 123");
        assertEquals(123, instruction.getOffset());

        instruction = InstructionJmp.fromString("jmp -123");
        assertEquals(-123, instruction.getOffset());

        instruction = InstructionJmp.fromString("jmp +321");
        assertEquals(321, instruction.getOffset());
    }

    @Test
    void test2015Day23JmpDoInstruction() {
        InstructionJmp instruction = new InstructionJmp(22);

        instruction.doInstruction(computer);

        assertEquals(10, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(23, computer.getPosition());
    }

    @Test
    void test2015Day23ParseJieOk() {
        InstructionJie instruction = InstructionJie.fromString("jie a, +2");
        assertEquals('a', instruction.getRegister());
        assertEquals(2, instruction.getOffset());

        instruction = InstructionJie.fromString("jie a, -65");
        assertEquals('a', instruction.getRegister());
        assertEquals(-65, instruction.getOffset());

        instruction = InstructionJie.fromString("jie b, 2");
        assertEquals('b', instruction.getRegister());
        assertEquals(2, instruction.getOffset());
    }

    @Test
    void test2015Day23JieDoInstructionJump() {
        InstructionJie instruction = new InstructionJie('a', 14);

        instruction.doInstruction(computer);

        assertEquals(10, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(15, computer.getPosition());
    }

    @Test
    void test2015Day23JieDoInstructionDontJump() {
        InstructionJie instruction = new InstructionJie('a', 14);

        computer.setRegister('a', 9);
        instruction.doInstruction(computer);

        assertEquals(9, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

    @Test
    void test2015Day23ParseJioOk() {
        InstructionJio instruction = InstructionJio.fromString("jio a, +2");
        assertEquals('a', instruction.getRegister());
        assertEquals(2, instruction.getOffset());

        instruction = InstructionJio.fromString("jio a, -65");
        assertEquals('a', instruction.getRegister());
        assertEquals(-65, instruction.getOffset());

        instruction = InstructionJio.fromString("jio b, 2");
        assertEquals('b', instruction.getRegister());
        assertEquals(2, instruction.getOffset());
    }

    @Test
    void test2015Day23JioDoInstructionJump() {
        InstructionJio instruction = new InstructionJio('a', 14);

        computer.setRegister('a', 1);
        instruction.doInstruction(computer);

        assertEquals(1, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(15, computer.getPosition());
    }

    @Test
    void test2015Day23JioDoInstructionDontJump() {
        InstructionJio instruction = new InstructionJio('a', 14);

        instruction.doInstruction(computer);

        assertEquals(10, computer.getRegister('a'));
        assertEquals(20, computer.getRegister('b'));
        assertEquals(2, computer.getPosition());
    }

}