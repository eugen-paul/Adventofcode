package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionDec implements Instruction {

    @Getter
    private char register;

    public static InstructionDec fromString(String data) {
        if (!data.startsWith("dec ")) {
            throw new IllegalArgumentException("Wrong InstructionDec: " + data);
        }
        return new InstructionDec(data.charAt(4));
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setRegister(register, computer.getRegister(register) - 1L);
        computer.setPosition(computer.getPosition() + 1);
    }
}
