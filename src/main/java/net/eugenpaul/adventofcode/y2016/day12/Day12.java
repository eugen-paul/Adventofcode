package net.eugenpaul.adventofcode.y2016.day12;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2016.day12.instruction.Instruction;
import net.eugenpaul.adventofcode.y2016.day12.instruction.InstructionFactory;

public class Day12 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day12.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day12.class.getName());

    @Getter
    private int a;

    @Getter
    private int a2;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2016/day12/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "a : " + getA());
        logger.log(Level.INFO, () -> "a2 : " + getA2());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());

        Computer computer = new Computer(List.of('a', 'b', 'c', 'd'), 0);
        a = doPuzzle(instructions, computer);

        Computer computer2 = new Computer(List.of('a', 'b', 'c', 'd'), 0);
        computer2.setRegister('c', 1);
        a2 = doPuzzle(instructions, computer2);

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
