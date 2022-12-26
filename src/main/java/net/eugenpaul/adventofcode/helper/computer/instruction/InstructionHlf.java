package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionHlf implements Instruction {

    @Getter
    private char register;

    public static InstructionHlf fromString(String data) {
        if (!data.startsWith("hlf ")) {
            return null;
        }
        return new InstructionHlf(data.charAt(4));
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setRegister(register, computer.getRegister(register) / 2);
        computer.setPosition(computer.getPosition() + 1);
    }
}
