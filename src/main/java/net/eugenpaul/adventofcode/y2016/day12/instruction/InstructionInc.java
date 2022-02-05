package net.eugenpaul.adventofcode.y2016.day12.instruction;

import net.eugenpaul.adventofcode.y2016.day12.Computer;

public class InstructionInc implements Instruction {

    private char register;

    public InstructionInc(char register) {
        this.register = register;
    }

    public static InstructionInc fromString(String data) {
        if (!data.startsWith("inc ")) {
            throw new IllegalArgumentException("Wrong InstructionInc: " + data);
        }
        return new InstructionInc(data.charAt(4));
    }

    public char getRegister() {
        return this.register;
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setRegister(register, computer.getRegister(register) + 1);
        computer.setPosition(computer.getPosition() + 1);
    }
}
