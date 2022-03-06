package net.eugenpaul.adventofcode.y2016.day25;

import net.eugenpaul.adventofcode.y2016.day12.instruction.Instruction;
import net.eugenpaul.adventofcode.y2016.day12.instruction.InstructionFactory;

public class InstructionFactoryExt {

    private InstructionFactoryExt() {

    }

    public static Instruction fromString(String data) {
        String prefix = data.substring(0, 3);

        Instruction response = null;
        if (prefix.equals("out")) {
            response = InstructionOut.fromString(data);
        } else {
            response = InstructionFactory.fromString(data);
        }

        return response;
    }
}
