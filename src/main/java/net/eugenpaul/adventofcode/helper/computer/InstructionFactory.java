package net.eugenpaul.adventofcode.helper.computer;

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
        case "jmp":
            response = InstructionJmp.fromString(data);
            break;
        case "jie":
            response = InstructionJie.fromString(data);
            break;
        case "jio":
            response = InstructionJio.fromString(data);
            break;
        case "tpl":
            response = InstructionTpl.fromString(data);
            break;
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
        case "jgz":
            response = InstructionJgz.fromString(data);
            break;
        case "set":
            response = InstructionSet.fromString(data);
            break;
        case "snd":
            response = InstructionSnd.fromString(data);
            break;
        case "add":
            response = InstructionAdd.fromString(data);
            break;
        case "sub":
            response = InstructionSub.fromString(data);
            break;
        case "mul":
            response = InstructionMul.fromString(data);
            break;
        case "mod":
            response = InstructionMod.fromString(data);
            break;
        case "rcv":
            response = InstructionRcv.fromString(data);
            break;
        default:
            throw new IllegalArgumentException("Wrong Instruction: " + data);
        }

        return response;
    }
}
