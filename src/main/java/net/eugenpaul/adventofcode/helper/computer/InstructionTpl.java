package net.eugenpaul.adventofcode.helper.computer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InstructionTpl implements Instruction {

    private char register;

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
