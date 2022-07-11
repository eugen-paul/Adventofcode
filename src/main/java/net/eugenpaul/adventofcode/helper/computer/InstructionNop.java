package net.eugenpaul.adventofcode.helper.computer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InstructionNop implements Instruction {

    public static InstructionNop fromString(String data) {
        if (!data.startsWith("nop ")) {
            return null;
        }
        return new InstructionNop();
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setPosition(computer.getPosition() + 1);
    }
}
