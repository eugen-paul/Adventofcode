package net.eugenpaul.adventofcode.y2015.day23.instruction;

import net.eugenpaul.adventofcode.y2015.day23.Computer;

public class InstructionTpl implements Instruction {

    private char register;

    public InstructionTpl(char register) {
        this.register = register;
    }

    public char getRegister() {
        return this.register;
    }

    public static InstructionTpl fromString(String data) {
        if (!data.startsWith("tpl ")) {
            return null;
        }
        return new InstructionTpl(data.charAt(4));
    }

    @Override
    public void doInstruction(Computer computer) {
        computer.setRegister(register, computer.getRegister(register) * 3);
        computer.setPosition(computer.getPosition() + 1);
    }
}
