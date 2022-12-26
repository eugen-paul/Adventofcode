package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionInp implements Instruction {

    public static final String WAITING_NAME = "WAIT";

    @Getter
    private char register;

    public static InstructionInp fromString(String data) {
        if (!data.startsWith("inp ")) {
            throw new IllegalArgumentException("Wrong InstructionInp: " + data);
        }
        return new InstructionInp(data.charAt(4));
    }

    @Override
    public void doInstruction(Computer computer) {
        Long value = computer.pop();
        if (value != null) {
            computer.setRegister(register, value);
            computer.setPosition(computer.getPosition() + 1);
            computer.setSpecialRegister(WAITING_NAME, 0);
        } else {
            computer.setSpecialRegister(WAITING_NAME, 1);
        }
    }
}
