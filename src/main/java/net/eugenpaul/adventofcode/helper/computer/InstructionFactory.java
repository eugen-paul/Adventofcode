package net.eugenpaul.adventofcode.helper.computer;

import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionAcc;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionAdd;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionCpy;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionDec;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionDiv;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionEql;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionHlf;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionInc;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionInp;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJgz;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJie;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJio;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJmp;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionJnz;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionMod;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionMul;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionNop;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionOut;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionRcv;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionSet;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionSnd;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionSub;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionTgl;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionTpl;

public class InstructionFactory {

    private InstructionFactory() {

    }

    public static Instruction fromString(String data) {
        String prefix = data.substring(0, 3);

        Instruction response = null;
        switch (prefix) {
        case "out":
            response = InstructionOut.fromString(data);
            break;
        case "div":
            response = InstructionDiv.fromString(data);
            break;
        case "eql":
            response = InstructionEql.fromString(data);
            break;
        case "tgl":
            response = InstructionTgl.fromString(data);
            break;
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
        case "inp":
            response = InstructionInp.fromString(data);
            break;
        case "nop":
            response = InstructionNop.fromString(data);
            break;
        case "acc":
            response = InstructionAcc.fromString(data);
            break;
        default:
            throw new IllegalArgumentException("Wrong Instruction: " + data);
        }

        return response;
    }
}
