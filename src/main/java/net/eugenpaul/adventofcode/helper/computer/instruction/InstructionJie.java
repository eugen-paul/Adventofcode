package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
@Getter
public class InstructionJie implements Instruction {

    private char register;
    private int offset;

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
