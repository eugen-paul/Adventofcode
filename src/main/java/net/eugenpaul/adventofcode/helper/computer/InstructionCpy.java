package net.eugenpaul.adventofcode.helper.computer;

import lombok.Getter;

@Getter
public class InstructionCpy implements Instruction {

    private Character from;
    private Character to;
    private Long value;

    public InstructionCpy(long value, char to) {
        this.from = null;
        this.to = to;
        this.value = value;
    }

    public InstructionCpy(char from, char to) {
        this.from = from;
        this.to = to;
        this.value = null;
    }

    public static InstructionCpy fromString(String data) {
        if (!data.startsWith("cpy ")) {
            throw new IllegalArgumentException("Wrong InstructionCpy: " + data);
        }
        String[] elements = data.split(" ");

        char to = elements[2].charAt(0);
        Long v = toNumber(elements[1]);
        if (v == null) {
            return new InstructionCpy(elements[1].charAt(0), to);
        }

        return new InstructionCpy(//
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
