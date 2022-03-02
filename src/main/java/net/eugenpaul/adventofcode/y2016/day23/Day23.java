package net.eugenpaul.adventofcode.y2016.day23;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2016.day12.Computer;
import net.eugenpaul.adventofcode.y2016.day12.instruction.Instruction;

public class Day23 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day23.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day23.class.getName());

    @Getter
    private int registerA;

    @Getter
    private int registerA2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2016/day23/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "registerA " + getRegisterA());
        logger.log(Level.INFO, () -> "registerA2 " + getRegisterA2());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactoryExt::fromString)//
                .collect(Collectors.toCollection(ArrayList::new));

        Computer computer = new Computer(List.of('a', 'b', 'c', 'd'), 0, instructions);
        computer.setRegister('a', 7);
        registerA = doPuzzle(instructions, computer);

        instructions = eventData.stream()//
                .map(InstructionFactoryExt::fromString)//
                .collect(Collectors.toCollection(ArrayList::new));

        computer = new Computer(List.of('a', 'b', 'c', 'd'), 0, instructions);
        computer.setRegister('a', 12);
        registerA2 = doPuzzle(instructions, computer);

        return true;
    }

    private int doPuzzle(List<Instruction> instructions, Computer computer) {

        while (computer.getPosition() < instructions.size()) {
            Instruction ins = instructions.get(computer.getPosition());
            ins.doInstruction(computer);
        }

        return computer.getRegister('a');
    }

}
