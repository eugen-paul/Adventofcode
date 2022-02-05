package net.eugenpaul.adventofcode.y2016.day12.instruction;

public class InstructionFactory {

    private InstructionFactory() {

    }

    public static Instruction fromString(String data) {
        String prefix = data.substring(0, 3);

        Instruction response = null;
        switch (prefix) {
        case "cpy":
            response = InstructionCpy.fromString(data);
            break;
        case "inc":
            response = InstructionInc.fromString(data);
            break;
        case "dec":
            response = InstructionDec.fromString(data);
            break;
        case "jnz":
            response = InstructionJnz.fromString(data);
            break;
        default:
            throw new IllegalArgumentException("Wrong InstructionJnz: " + data);
        }

        return response;
    }
}
