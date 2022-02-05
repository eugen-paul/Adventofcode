package net.eugenpaul.adventofcode.y2016.day12.instruction;

import lombok.Getter;
import net.eugenpaul.adventofcode.y2016.day12.Computer;

@Getter
public class InstructionJnz implements Instruction {

    private int offset;
    private Character checkRegister;
    private Integer value;

    public InstructionJnz(char checkRegister, int offset) {
        this.checkRegister = checkRegister;
        this.offset = offset;
        this.value = null;
    }

    public InstructionJnz(int value, int offset) {
        this.value = value;
        this.checkRegister = null;
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }

    public static InstructionJnz fromString(String data) {
        if (!data.startsWith("jnz ")) {
            throw new IllegalArgumentException("Wrong InstructionJnz: " + data);
        }
        String[] elements = data.split(" ");

        int offset = Integer.parseInt(elements[2]);
        if (elements[1].charAt(0) < '0' || elements[1].charAt(0) > '9') {
            return new InstructionJnz(elements[1].charAt(0), Integer.parseInt(elements[2]));
        }

        return new InstructionJnz(//
                Integer.parseInt(elements[1]), //
                offset);
    }

    @Override
    public void doInstruction(Computer computer) {
        if ((value != null && value != 0)//
                || (computer.getRegister(checkRegister) != null && computer.getRegister(checkRegister) != 0)//
        ) {
            computer.setPosition(computer.getPosition() + offset);
        } else {
            computer.setPosition(computer.getPosition() + 1);
        }
    }
}
