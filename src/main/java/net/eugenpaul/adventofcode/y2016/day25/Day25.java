package net.eugenpaul.adventofcode.y2016.day25;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2016.day12.Computer;
import net.eugenpaul.adventofcode.y2016.day12.instruction.Instruction;

public class Day25 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day25.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day25.class.getName());

    @Getter
    private int a;
    private int longestSignal;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2016/day25/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "a " + getA());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactoryExt::fromString)//
                .collect(Collectors.toList());

        a = 0;

        longestSignal = 0;

        while (!doPuzzle(instructions, a)) {
            a++;
        }

        return true;
    }

    private boolean doPuzzle(List<Instruction> instructions, int a) {
        Computer computer = new Computer(List.of('a', 'b', 'c', 'd', 'o', 'p'), 0);
        computer.setRegister('a', a);
        computer.setRegister('o', 1);
        computer.setRegister('p', 0);

        try {
            //my forever ends by 100 signals
            while (computer.getPosition() < instructions.size() && computer.getRegister('p') < 100) {
                Instruction ins = instructions.get(computer.getPosition());
                ins.doInstruction(computer);
            }
        } catch (IllegalArgumentException e) {
            if (computer.getRegister('p') > longestSignal) {
                longestSignal = computer.getRegister('p');
            }
            return false;
        }

        return true;
    }

}
