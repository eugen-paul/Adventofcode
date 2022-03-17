package net.eugenpaul.adventofcode.y2017.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day8 extends SolutionTemplate {

    @Getter
    private Integer largestValue;
    @Getter
    private Integer largestValueEver;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2017/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        List<Instruction> instructions = eventData.stream().map(Instruction::fromString).collect(Collectors.toList());

        Map<String, Integer> registers = new HashMap<>();

        largestValueEver = 0;
        instructions.stream().forEach(v -> doInstruction(v, registers));

        largestValue = registers.values().stream().sorted().reduce((first, second) -> second).orElse(null);

        logger.log(Level.INFO, () -> "largestValue : " + getLargestValue());
        logger.log(Level.INFO, () -> "largestValueEver : " + getLargestValueEver());

        return true;
    }

    private void doInstruction(Instruction instruction, Map<String, Integer> registers) {
        int targetRegisterValue = registers.computeIfAbsent(instruction.getTargetRegister(), k -> 0);
        int compareRegisterValue = registers.computeIfAbsent(instruction.getCheckRegister(), k -> 0);

        boolean isCompOk = false;
        switch (instruction.getEq()) {
        case ">":
            isCompOk = compareRegisterValue > instruction.getCheckValue();
            break;
        case "<":
            isCompOk = compareRegisterValue < instruction.getCheckValue();
            break;
        case "!=":
            isCompOk = compareRegisterValue != instruction.getCheckValue();
            break;
        case "<=":
            isCompOk = compareRegisterValue <= instruction.getCheckValue();
            break;
        case ">=":
            isCompOk = compareRegisterValue >= instruction.getCheckValue();
            break;
        case "==":
            isCompOk = compareRegisterValue == instruction.getCheckValue();
            break;
        default:
            throw new IllegalArgumentException(instruction.getEq());
        }

        if (isCompOk) {
            if (instruction.isInc()) {
                targetRegisterValue += instruction.getModificator();
            } else {
                targetRegisterValue -= instruction.getModificator();
            }
            largestValueEver = Math.max(largestValueEver, targetRegisterValue);
            registers.put(instruction.getTargetRegister(), targetRegisterValue);
        }
    }

}
