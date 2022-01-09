package net.eugenpaul.adventofcode.y2015.day23.instruction;

import net.eugenpaul.adventofcode.y2015.day23.Computer;

public class InstructionJmp implements Instruction {

    private int offset;

    public InstructionJmp(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }

    public static InstructionJmp fromString(String data) {
        if (!data.startsWith("jmp ")) {
            return null;
        }
        return new InstructionJmp(Integer.parseInt(data.substring(4)));
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setPosition(computer.getPosition() + offset);
    }
}
