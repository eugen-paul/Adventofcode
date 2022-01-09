package net.eugenpaul.adventofcode.y2015.day23.instruction;

import net.eugenpaul.adventofcode.y2015.day23.Computer;

public class InstructionJio implements Instruction {

    private char register;
    private int offset;

    public char getRegister() {
        return this.register;
    }

    public int getOffset() {
        return this.offset;
    }

    public InstructionJio(char register, int offset) {
        this.register = register;
        this.offset = offset;
    }

    public static InstructionJio fromString(String data) {
        if (!data.startsWith("jio ")) {
            return null;
        }
        return new InstructionJio(data.charAt(4), Integer.parseInt(data.substring(7)));
    }

    @Override
    public void doInstruction(Computer computer) {
        if (computer.getRegister(register) == 1) {
            computer.setPosition(computer.getPosition() + offset);
        } else {
            computer.setPosition(computer.getPosition() + 1);
        }
    }
}
