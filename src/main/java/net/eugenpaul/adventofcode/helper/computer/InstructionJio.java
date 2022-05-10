package net.eugenpaul.adventofcode.helper.computer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InstructionJio implements Instruction {

    private char register;
    private int offset;

    public static InstructionJio fromString(String data) {
        if (!data.startsWith("jio ")) {
            return null;
        }
        return new InstructionJio(data.charAt(4), Integer.parseInt(data.substring(7)));
    }

    @Override
    public void doInstruction(Computer computer) {
        if (computer.getRegister(register) == 1) {
            computer.setPosition(computer.getPosition() + offset);
        } else {
            computer.setPosition(computer.getPosition() + 1);
        }
    }
}
