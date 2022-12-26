package net.eugenpaul.adventofcode.helper.computer.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionJmp implements Instruction {

    @Getter
    private int offset;

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
