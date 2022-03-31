package net.eugenpaul.adventofcode.helper.computer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InstructionInc implements Instruction {

    @Getter
    private char register;

    public static InstructionInc fromString(String data) {
        if (!data.startsWith("inc ")) {
            throw new IllegalArgumentException("Wrong InstructionInc: " + data);
        }
        return new InstructionInc(data.charAt(4));
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setRegister(register, computer.getRegister(register) + 1L);
        computer.setPosition(computer.getPosition() + 1);
    }
}
