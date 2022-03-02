package net.eugenpaul.adventofcode.y2016.day12.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.y2016.day12.Computer;

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
        computer.setRegister(register, computer.getRegister(register) - 1);
        computer.setPosition(computer.getPosition() + 1);
    }
}
