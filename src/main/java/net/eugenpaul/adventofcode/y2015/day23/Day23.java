package net.eugenpaul.adventofcode.y2015.day23;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2015.day23.instruction.Instruction;
import net.eugenpaul.adventofcode.y2015.day23.instruction.InstructionFactory;

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

    private long registerA;
    private long registerB;

    public long getRegisterA() {
        return registerA;
    }

    public long getRegisterB() {
        return registerB;
    }

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2015/day23/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData, 0)) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "Test 1: registerA: " + getRegisterA());
        logger.log(Level.INFO, () -> "Test 1: registerB: " + getRegisterB());

        doEvent(eventData, 1);
        logger.log(Level.INFO, () -> "Test 2: registerA: " + getRegisterA());
        logger.log(Level.INFO, () -> "Test 2: registerB: " + getRegisterB());

        return true;
    }

    public boolean doEvent(List<String> eventData, long a) {
        if (null == eventData) {
            return false;
        }

        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());
        Computer computer = new Computer(a, 0, 0);

        while (0 <= computer.getPosition() && computer.getPosition() < instructions.size()) {
            instructions.get(computer.getPosition()).doInstruction(computer);
        }

        registerA = computer.getRegister('a');
        registerB = computer.getRegister('b');

        return true;
    }

}
