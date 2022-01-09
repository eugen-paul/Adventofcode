package net.eugenpaul.adventofcode.y2015.day23.instruction;

import net.eugenpaul.adventofcode.y2015.day23.Computer;

public class InstructionHlf implements Instruction {

    private char register;

    public InstructionHlf(char register) {
        this.register = register;
    }

    public char getRegister() {
        return this.register;
    }

    public static InstructionHlf fromString(String data) {
        if (!data.startsWith("hlf ")) {
            return null;
        }
        return new InstructionHlf(data.charAt(4));
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setRegister(register, computer.getRegister(register) / 2);
        computer.setPosition(computer.getPosition() + 1);
    }
}
