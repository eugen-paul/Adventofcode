package net.eugenpaul.adventofcode.y2016.day23;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;
import net.eugenpaul.adventofcode.helper.computer.InstructionFactory;

public class Day23 extends SolutionTemplate {

    @Getter
    private int registerA;

    @Getter
    private int registerA2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2016/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toCollection(ArrayList::new));

        Computer computer = new Computer(List.of('a', 'b', 'c', 'd'), 0, instructions);
        computer.setRegister('a', 7);
        registerA = doPuzzle(instructions, computer);

        instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toCollection(ArrayList::new));

        computer = new Computer(List.of('a', 'b', 'c', 'd'), 0, instructions);
        computer.setRegister('a', 12);
        registerA2 = doPuzzle(instructions, computer);

        logger.log(Level.INFO, () -> "registerA " + getRegisterA());
        logger.log(Level.INFO, () -> "registerA2 " + getRegisterA2());

        return true;
    }

    private int doPuzzle(List<Instruction> instructions, Computer computer) {

        while (computer.getPosition() < instructions.size()) {
            Instruction ins = instructions.get(computer.getPosition());
            ins.doInstruction(computer);
        }

        return computer.getRegister('a').intValue();
    }

}
