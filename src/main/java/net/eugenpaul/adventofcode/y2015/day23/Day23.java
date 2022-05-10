package net.eugenpaul.adventofcode.y2015.day23;

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
    private long registerB;

    @Getter
    private long registerBPuzzle2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2015/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        registerB = doPuzzle(eventData, 0);

        logger.log(Level.INFO, () -> "Test 1: registerB: " + getRegisterB());

        registerBPuzzle2 = doPuzzle(eventData, 1);
        logger.log(Level.INFO, () -> "Test 2: registerB: " + getRegisterBPuzzle2());

        return true;
    }

    private long doPuzzle(List<String> eventData, long a) {
        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());
        Computer computer = new Computer(List.of('a', 'b'), 0);
        computer.setRegister('a', a);

        while (0 <= computer.getPosition() && computer.getPosition() < instructions.size()) {
            instructions.get(computer.getPosition()).doInstruction(computer);
        }

        return computer.getRegister('b');
    }

}
