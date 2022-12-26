package net.eugenpaul.adventofcode.y2017.day18;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;
import net.eugenpaul.adventofcode.helper.computer.InstructionFactory;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionRcv;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionSnd;

public class Day18 extends SolutionTemplate {

    @Getter
    private Long value;

    @Getter
    private Long sendCounter;

    @Setter
    private boolean doPuzzle1 = true;
    @Setter
    private boolean doPuzzle2 = true;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2017/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        value = doPuzzle1(eventData);
        sendCounter = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "value : " + getValue());
        logger.log(Level.INFO, () -> "sendCounter : " + getSendCounter());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        if (!doPuzzle1) {
            return -1L;
        }
        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactoryDay18Puzzle1::fromString)//
                .collect(Collectors.toList());

        LinkedList<Long> queue = new LinkedList<>();
        Computer computer = new Computer(0, 0, queue, queue);
        computer.setSpecialRegister(InstructionRcvDay18Puzzle1.REGISTER_NAME, -1);

        while (computer.getSpecialRegister(InstructionRcvDay18Puzzle1.REGISTER_NAME) < 0) {
            Instruction ins = instructions.get(computer.getPosition());
            ins.doInstruction(computer);
        }

        return computer.getSpecialRegister(InstructionRcvDay18Puzzle1.REGISTER_NAME);
    }

    private long doPuzzle2(List<String> eventData) {
        if (!doPuzzle2) {
            return -1L;
        }
        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());

        LinkedList<Long> queue0 = new LinkedList<>();
        LinkedList<Long> queue1 = new LinkedList<>();

        Computer computer0 = new Computer(0, 0, queue0, queue1);
        computer0.setRegister('p', 0);

        Computer computer1 = new Computer(0, 0, queue1, queue0);
        computer1.setRegister('p', 1);

        while (computer0.getSpecialRegister(InstructionRcv.WAITING_NAME) == 0 //
                || computer1.getSpecialRegister(InstructionRcv.WAITING_NAME) == 0//
        ) {
            Instruction ins = instructions.get(computer0.getPosition());
            ins.doInstruction(computer0);

            ins = instructions.get(computer1.getPosition());
            ins.doInstruction(computer1);
        }

        return computer1.getSpecialRegister(InstructionSnd.COUNTER);
    }

}
