package net.eugenpaul.adventofcode.helper.computer;

import lombok.Getter;

@Getter
public class InstructionJgz implements Instruction {

    private Long offset;
    private Character checkRegister;
    private Long value;
    private Character registerValue;

    public InstructionJgz(char checkRegister, long offset) {
        this.checkRegister = checkRegister;
        this.offset = offset;
        this.value = null;
        this.registerValue = null;
    }

    public InstructionJgz(long value, long offset) {
        this.value = value;
        this.checkRegister = null;
        this.offset = offset;
        this.registerValue = null;
    }

    public InstructionJgz(long value, char registerValue) {
        this.value = value;
        this.checkRegister = null;
        this.offset = null;
        this.registerValue = registerValue;
    }

    public InstructionJgz(char checkRegister, char registerValue) {
        this.value = null;
        this.checkRegister = checkRegister;
        this.offset = null;
        this.registerValue = registerValue;
    }

    public static InstructionJgz fromString(String data) {
        if (!data.startsWith("jgz ")) {
            throw new IllegalArgumentException("Wrong InstructionJgz: " + data);
        }
        String[] elements = data.split(" ");

        Long param = toNumber(elements[1]);
        Long offset = toNumber(elements[2]);
        if (param != null && offset != null) {
            return new InstructionJgz(param, offset);
        }

        if (param != null) {
            return new InstructionJgz(param, elements[2].charAt(0));
        }

        if (offset != null) {
            return new InstructionJgz(elements[1].charAt(0), offset);
        }

        return new InstructionJgz(elements[1].charAt(0), elements[2].charAt(0));
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
        if ((value != null && value > 0L)//
                || (checkRegister != null && computer.getRegister(checkRegister) != null && computer.getRegister(checkRegister) > 0L)//
        ) {
            if (offset == null) {
                computer.setPosition((int) (computer.getPosition() + computer.getRegister(registerValue)));
            } else {
                computer.setPosition((int) (computer.getPosition() + offset));
            }
        } else {
            computer.setPosition(computer.getPosition() + 1);
        }
    }
}
