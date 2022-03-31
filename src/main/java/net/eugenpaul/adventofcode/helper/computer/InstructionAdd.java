package net.eugenpaul.adventofcode.helper.computer;

import lombok.Getter;

@Getter
public class InstructionAdd implements Instruction {

    private Character from;
    private Character to;
    private Long value;

    public InstructionAdd(long value, char to) {
        this.from = null;
        this.to = to;
        this.value = value;
    }

    public InstructionAdd(char from, char to) {
        this.from = from;
        this.to = to;
        this.value = null;
    }

    public static InstructionAdd fromString(String data) {
        if (!data.startsWith("add ")) {
            throw new IllegalArgumentException("Wrong InstructionAdd: " + data);
        }
        String[] elements = data.split(" ");

        char to = elements[1].charAt(0);
        Long v = toNumber(elements[2]);
        if (v == null) {
            return new InstructionAdd(elements[2].charAt(0), to);
        }

        return new InstructionAdd(//
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
            computer.setRegister(to, computer.getRegister(to) + computer.getRegister(from));
        } else {
            computer.setRegister(to, computer.getRegister(to) + value);
        }
        computer.setPosition(computer.getPosition() + 1);
    }
}
