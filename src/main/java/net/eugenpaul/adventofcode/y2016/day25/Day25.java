package net.eugenpaul.adventofcode.y2016.day25;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;
import net.eugenpaul.adventofcode.helper.computer.InstructionFactory;

public class Day25 extends SolutionTemplate {

    @Getter
    private int a;
    private int longestSignal;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2016/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());

        a = 0;

        longestSignal = 0;

        while (!doPuzzle(instructions, a)) {
            a++;
        }

        logger.log(Level.INFO, () -> "a " + getA());
        return true;
    }

    private boolean doPuzzle(List<Instruction> instructions, int a) {
        Computer computer = new Computer(List.of('a', 'b', 'c', 'd', 'o', 'p'), 0);
        computer.setRegister('a', a);
        computer.setRegister('o', 1);
        computer.setRegister('p', 0);

        try {
            // my forever ends by 100 signals
            while (computer.getPosition() < instructions.size() && computer.getRegister('p') < 100) {
                Instruction ins = instructions.get(computer.getPosition());
                ins.doInstruction(computer);
            }
        } catch (IllegalArgumentException e) {
            if (computer.getRegister('p') > longestSignal) {
                longestSignal = computer.getRegister('p').intValue();
            }
            return false;
        }

        return true;
    }

}
