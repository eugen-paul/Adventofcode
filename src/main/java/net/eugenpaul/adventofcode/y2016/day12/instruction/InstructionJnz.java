package net.eugenpaul.adventofcode.y2016.day12.instruction;

import lombok.Getter;
import net.eugenpaul.adventofcode.y2016.day12.Computer;

@Getter
public class InstructionJnz implements Instruction {

    private Integer offset;
    private Character checkRegister;
    private Integer value;
    private Character registerValue;

    public InstructionJnz(char checkRegister, int offset) {
        this.checkRegister = checkRegister;
        this.offset = offset;
        this.value = null;
        this.registerValue = null;
    }

    public InstructionJnz(int value, int offset) {
        this.value = value;
        this.checkRegister = null;
        this.offset = offset;
        this.registerValue = null;
    }

    // We need this for day 23
    public InstructionJnz(int value, char registerValue) {
        this.value = value;
        this.checkRegister = null;
        this.offset = null;
        this.registerValue = registerValue;
    }

    // We need this for day 23
    public InstructionJnz(char checkRegister, char registerValue) {
        this.value = null;
        this.checkRegister = checkRegister;
        this.offset = null;
        this.registerValue = registerValue;
    }

    public static InstructionJnz fromString(String data) {
        if (!data.startsWith("jnz ")) {
            throw new IllegalArgumentException("Wrong InstructionJnz: " + data);
        }
        String[] elements = data.split(" ");

        Integer param = toNumber(elements[1]);
        Integer offset = toNumber(elements[2]);
        if (param != null && offset != null) {
            return new InstructionJnz(param, offset);
        }

        if (param != null) {
            return new InstructionJnz(param, elements[2].charAt(0));
        }

        if (offset != null) {
            return new InstructionJnz(elements[1].charAt(0), offset);
        }

        return new InstructionJnz(elements[1].charAt(0), elements[2].charAt(0));
    }

    private static Integer toNumber(String input) {
        try {
            return Integer.parseInt(input); //
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void doInstruction(Computer computer) {
        if ((value != null && value != 0)//
                || (checkRegister != null && computer.getRegister(checkRegister) != null && computer.getRegister(checkRegister) != 0)//
        ) {
            if (offset == null) {
                computer.setPosition(computer.getPosition() + computer.getRegister(registerValue));
            } else {
                computer.setPosition(computer.getPosition() + offset);
            }
        } else {
            computer.setPosition(computer.getPosition() + 1);
        }
    }
}
