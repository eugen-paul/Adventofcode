package net.eugenpaul.adventofcode.helper.computer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InstructionAcc implements Instruction {

    @Getter
    private int value;

    public static InstructionAcc fromString(String data) {
        if (!data.startsWith("acc ")) {
            return null;
        }
        return new InstructionAcc(Integer.parseInt(data.substring(4)));
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setSpecialRegister("acc", computer.getSpecialRegister("acc") + value);
        computer.setPosition(computer.getPosition() + 1);
    }
}
