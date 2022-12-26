package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@Getter
public class InstructionDiv implements Instruction {

    private Character from;
    private Character to;
    private Long value;

    public InstructionDiv(long value, char to) {
        this.from = null;
        this.to = to;
        this.value = value;
    }

    public InstructionDiv(char from, char to) {
        this.from = from;
        this.to = to;
        this.value = null;
    }

    public static InstructionDiv fromString(String data) {
        if (!data.startsWith("div ")) {
            throw new IllegalArgumentException("Wrong InstructionDiv: " + data);
        }
        String[] elements = data.split(" ");

        char to = elements[1].charAt(0);
        Long v = toNumber(elements[2]);
        if (v == null) {
            return new InstructionDiv(elements[2].charAt(0), to);
        }

        return new InstructionDiv(//
                v, //
                to);
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
            computer.setRegister(to, computer.getRegister(to) / computer.getRegister(from));
        } else {
            computer.setRegister(to, computer.getRegister(to) / value);
        }
        computer.setPosition(computer.getPosition() + 1);
    }
}
