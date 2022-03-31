package net.eugenpaul.adventofcode.y2017.day18;

import net.eugenpaul.adventofcode.helper.computer.Instruction;
import net.eugenpaul.adventofcode.helper.computer.InstructionFactory;

public class InstructionFactoryDay18Puzzle1 {

    private InstructionFactoryDay18Puzzle1() {

    }

    public static Instruction fromString(String data) {
        String prefix = data.substring(0, 3);

        Instruction response = null;
        switch (prefix) {
        case "rcv":
            response = InstructionRcvDay18Puzzle1.fromString(data);
            break;
        case "snd":
            response = InstructionSndDay18Puzzle1.fromString(data);
            break;
        default:
            response = InstructionFactory.fromString(data);
        }

        return response;
    }
}
