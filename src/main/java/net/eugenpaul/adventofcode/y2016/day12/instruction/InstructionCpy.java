package net.eugenpaul.adventofcode.y2016.day12.instruction;

import lombok.Getter;
import net.eugenpaul.adventofcode.y2016.day12.Computer;

@Getter
public class InstructionCpy implements Instruction {

    private Character from;
    private Character to;
    private Integer value;

    public InstructionCpy(int value, char to) {
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
        if (elements[1].charAt(0) < '0' || elements[1].charAt(0) > '9') {
            return new InstructionCpy(elements[1].charAt(0), elements[2].charAt(0));
        }

        return new InstructionCpy(//
                Integer.parseInt(elements[1]), //
                to);
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
