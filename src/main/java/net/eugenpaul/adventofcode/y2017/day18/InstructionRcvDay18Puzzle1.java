package net.eugenpaul.adventofcode.y2017.day18;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionRcvDay18Puzzle1 implements Instruction {

    public static final String REGISTER_NAME = "RCV";

    @Getter
    private char register;

    public static InstructionRcvDay18Puzzle1 fromString(String data) {
        if (!data.startsWith("rcv ")) {
            throw new IllegalArgumentException("Wrong InstructionRcv0: " + data);
        }
        return new InstructionRcvDay18Puzzle1(data.charAt(4));
    }

    @Override
    public void doInstruction(Computer computer) {
        if (computer.getRegister(register) != 0) {
            computer.setSpecialRegister(REGISTER_NAME, computer.getSpecialRegister(InstructionSndDay18Puzzle1.REGISTER_NAME));
            computer.setSpecialRegister(InstructionSndDay18Puzzle1.REGISTER_NAME, 0);
        }
        computer.setPosition(computer.getPosition() + 1);
    }
}
