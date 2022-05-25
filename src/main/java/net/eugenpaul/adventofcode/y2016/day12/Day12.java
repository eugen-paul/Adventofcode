package net.eugenpaul.adventofcode.y2016.day12;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;
import net.eugenpaul.adventofcode.helper.computer.InstructionFactory;

public class Day12 extends SolutionTemplate {

    @Getter
    private int a;

    @Getter
    private int a2;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2016/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());

        Computer computer = new Computer(List.of('a', 'b', 'c', 'd'), 0);
        a = doPuzzle(instructions, computer);

        Computer computer2 = new Computer(List.of('a', 'b', 'c', 'd'), 0);
        computer2.setRegister('c', 1);
        a2 = doPuzzle(instructions, computer2);

        logger.log(Level.INFO, () -> "a : " + getA());
        logger.log(Level.INFO, () -> "a2 : " + getA2());

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
