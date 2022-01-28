package net.eugenpaul.adventofcode.y2016.day10;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day10 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day10.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day10.class.getName());

    @Getter
    private int bot;
    @Getter
    private int multiplyValues;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2016/day10/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData, 17, 61);
    }

    public boolean doPuzzleFromData(List<String> eventData, int lowValue, int highValue) {
        if (!doEvent(eventData, lowValue, highValue)) {
            return false;
        }

        logger.log(Level.INFO, () -> "bot: " + getBot());
        logger.log(Level.INFO, () -> "multiplyValues: " + getMultiplyValues());

        return true;
    }

    private boolean doEvent(List<String> eventData, int lowValue, int highValue) {
        if (null == eventData) {
            return false;
        }

        InstructionFactory factory = new InstructionFactory(lowValue, highValue);

        eventData.stream().forEach(factory::readInstruction);
        factory.doInit();
        factory.doInstructions();

        bot = factory.getBotnumber();
        multiplyValues = factory.getMultiplyValues();

        return true;
    }
}
