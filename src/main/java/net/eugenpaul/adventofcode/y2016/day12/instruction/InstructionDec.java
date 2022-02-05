package net.eugenpaul.adventofcode.y2016.day12.instruction;

import net.eugenpaul.adventofcode.y2016.day12.Computer;

public class InstructionDec implements Instruction {

    private char register;

    public InstructionDec(char register) {
        this.register = register;
    }

    public static InstructionDec fromString(String data) {
        if (!data.startsWith("dec ")) {
            throw new IllegalArgumentException("Wrong InstructionDec: " + data);
        }
        return new InstructionDec(data.charAt(4));
    }

    public char getRegister() {
        return this.register;
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setRegister(register, computer.getRegister(register) - 1);
        computer.setPosition(computer.getPosition() + 1);
    }
}
