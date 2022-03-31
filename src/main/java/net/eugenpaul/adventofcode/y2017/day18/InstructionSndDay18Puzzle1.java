package net.eugenpaul.adventofcode.y2017.day18;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionSndDay18Puzzle1 implements Instruction {

    public static final String REGISTER_NAME = "SND";

    @Getter
    private Long value;
    @Getter
    private Character register;

    public static InstructionSndDay18Puzzle1 fromString(String data) {
        if (!data.startsWith("snd ")) {
            throw new IllegalArgumentException("Wrong InstructionSnd: " + data);
        }

        Long numberValue = toNumber(data.substring(4));
        if (numberValue == null) {
            return new InstructionSndDay18Puzzle1(null, data.charAt(4));
        }

        return new InstructionSndDay18Puzzle1(numberValue, null);
    }

    private static Long toNumber(String input) {
        try {
            return Long.parseLong(input); //
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void doInstruction(Computer computer) {
        if (value == null) {
            computer.setSpecialRegister(REGISTER_NAME, computer.getRegister(register.charValue()));
        } else {
            computer.setSpecialRegister(REGISTER_NAME, value);
        }
        computer.setPosition(computer.getPosition() + 1);
    }
}
