package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@Getter
public class InstructionOut implements Instruction {

    private Character register;

    public InstructionOut(char register) {
        this.register = register;
    }

    public static InstructionOut fromString(String data) {
        if (!data.startsWith("out ")) {
            throw new IllegalArgumentException("Wrong InstructionOut: " + data);
        }
        String[] elements = data.split(" ");

        return new InstructionOut(elements[1].charAt(0));
    }

    @Override
    public void doInstruction(Computer computer) {
        int currentSignal = computer.getRegister(register).intValue();
        int lastSignal = computer.getRegister('o').intValue();

        if (currentSignal != 0 && currentSignal != 1) {
            // wrong signal => break
            throw new IllegalArgumentException();
        }

        if (currentSignal == lastSignal) {
            // wrong signal => break
            throw new IllegalArgumentException();
        }

        computer.setRegister('o', computer.getRegister(register));
        computer.setRegister('p', computer.getRegister('p') + 1);
        computer.setPosition(computer.getPosition() + 1);
    }
}
