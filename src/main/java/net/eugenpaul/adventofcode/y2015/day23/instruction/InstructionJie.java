package net.eugenpaul.adventofcode.y2015.day23.instruction;

import net.eugenpaul.adventofcode.y2015.day23.Computer;

public class InstructionJie implements Instruction {

    private char register;
    private int offset;

    public InstructionJie(char register, int offset) {
        this.register = register;
        this.offset = offset;
    }

    public char getRegister() {
        return this.register;
    }

    public int getOffset() {
        return this.offset;
    }

    public static InstructionJie fromString(String data) {
        if (!data.startsWith("jie ")) {
            return null;
        }
        return new InstructionJie(data.charAt(4), Integer.parseInt(data.substring(7)));
    }

    @Override
    public void doInstruction(Computer computer) {
        if (computer.getRegister(register) % 2 == 0) {
            computer.setPosition(computer.getPosition() + offset);
        } else {
            computer.setPosition(computer.getPosition() + 1);
        }
    }
}
