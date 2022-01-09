package net.eugenpaul.adventofcode.y2015.day23.instruction;

public class InstructionFactory {

    private InstructionFactory() {

    }

    public static Instruction fromString(String data) {
        String prefix = data.substring(0, 3);

        Instruction response = null;
        switch (prefix) {
        case "hlf":
            response = InstructionHlf.fromString(data);
            break;
        case "tpl":
            response = InstructionTpl.fromString(data);
            break;
        case "inc":
            response = InstructionInc.fromString(data);
            break;
        case "jmp":
            response = InstructionJmp.fromString(data);
            break;
        case "jie":
            response = InstructionJie.fromString(data);
            break;
        case "jio":
            response = InstructionJio.fromString(data);
            break;
        default:
            break;
        }

        return response;
    }
}
