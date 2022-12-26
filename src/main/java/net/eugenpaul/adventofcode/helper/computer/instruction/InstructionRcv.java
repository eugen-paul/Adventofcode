package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionRcv implements Instruction {

    public static final String WAITING_NAME = "WAIT";

    @Getter
    private char register;

    public static InstructionRcv fromString(String data) {
        if (!data.startsWith("rcv ")) {
            throw new IllegalArgumentException("Wrong InstructionRcv: " + data);
        }
        return new InstructionRcv(data.charAt(4));
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
