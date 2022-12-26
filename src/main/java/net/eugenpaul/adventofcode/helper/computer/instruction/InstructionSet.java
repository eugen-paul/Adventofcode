package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@Getter
public class InstructionSet implements Instruction {

    private Character from;
    private Character to;
    private Long value;

    public InstructionSet(long value, char to) {
        this.from = null;
        this.to = to;
        this.value = value;
    }

    public InstructionSet(char from, char to) {
        this.from = from;
        this.to = to;
        this.value = null;
    }

    public static InstructionSet fromString(String data) {
        if (!data.startsWith("set ")) {
            throw new IllegalArgumentException("Wrong InstructionSet: " + data);
        }
        String[] elements = data.split(" ");

        char to = elements[1].charAt(0);
        Long v = toNumber(elements[2]);
        if (v == null) {
            return new InstructionSet(elements[2].charAt(0), to);
        }

        return new InstructionSet(//
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
            computer.setRegister(to, computer.getRegister(from));
        } else {
            computer.setRegister(to, value);
        }
        computer.setPosition(computer.getPosition() + 1);
    }
}
