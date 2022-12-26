package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@Getter
/**
 * eql a b - If the value of a and b are equal, then store the value 1 in variable a. Otherwise, store the value 0 in variable a.
 */
public class InstructionEql implements Instruction {

    private Character a;
    private Character b;
    private Long bValue;

    public InstructionEql(long bValue, char a) {
        this.b = null;
        this.a = a;
        this.bValue = bValue;
    }

    public InstructionEql(char b, char a) {
        this.b = b;
        this.a = a;
        this.bValue = null;
    }

    public static InstructionEql fromString(String data) {
        if (!data.startsWith("eql ")) {
            throw new IllegalArgumentException("Wrong InstructionEql: " + data);
        }
        String[] elements = data.split(" ");

        char a = elements[1].charAt(0);
        Long b = toNumber(elements[2]);
        if (b == null) {
            return new InstructionEql(elements[2].charAt(0), a);
        }

        return new InstructionEql(//
                b, //
                a);
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
        if (bValue == null) {
            if (computer.getRegister(a).longValue() == computer.getRegister(b).longValue()) {
                computer.setRegister(a, 1);
            } else {
                computer.setRegister(a, 0);
            }
        } else {
            if (computer.getRegister(a).longValue() == bValue.longValue()) {
                computer.setRegister(a, 1);
            } else {
                computer.setRegister(a, 0);
            }
        }
        computer.setPosition(computer.getPosition() + 1);
    }
}
