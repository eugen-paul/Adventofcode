package net.eugenpaul.adventofcode.helper.computer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InstructionSnd implements Instruction {

    public static final String COUNTER = "SND_CNT";

    @Getter
    private Long value;
    @Getter
    private Character register;

    public static InstructionSnd fromString(String data) {
        if (!data.startsWith("snd ")) {
            throw new IllegalArgumentException("Wrong InstructionSnd: " + data);
        }

        Long numberValue = toNumber(data.substring(4));
        if (numberValue == null) {
            return new InstructionSnd(null, data.charAt(4));
        }

        return new InstructionSnd(numberValue, null);
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
            computer.push(computer.getRegister(register.charValue()));
        } else {
            computer.push(value);
        }
        computer.setSpecialRegister(COUNTER, computer.getSpecialRegister(COUNTER) + 1);
        computer.setPosition(computer.getPosition() + 1);
    }
}
