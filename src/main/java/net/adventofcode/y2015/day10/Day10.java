package net.adventofcode.y2015.day10;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.adventofcode.helper.FileReaderHelper;

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

    private String resultString;

    public String getResult() {
        return resultString;
    }

    public static void main(String[] args) {
        Day10 puzzle1 = new Day10();
        puzzle1.doPuzzleFromFile("y2015/day10/puzzle1.txt", 40);

        Day10 puzzle2 = new Day10();
        puzzle2.doPuzzleFromData(puzzle1.getResult(), 10);
    }

    public boolean doPuzzleFromFile(String filename, int steps) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData, steps);
    }

    public boolean doPuzzleFromData(String eventData, int steps) {
        if (!doEvent(eventData, steps)) {
            return false;
        }

        logger.log(Level.INFO, () -> "resultString.length: " + getResult().length());

        return true;
    }

    private boolean doEvent(String eventData, int steps) {
        if (null == eventData) {
            return false;
        }

        String result = eventData;
        for (int i = 0; i < steps; i++) {
            result = toStep(result);
        }

        resultString = result;

        return true;
    }

    public String toStep(String input) {
        char lastDigit = input.charAt(0);
        int digitCount = 1;

        StringBuilder result = new StringBuilder(input.length() * 2);

        for (char digit : input.substring(1).toCharArray()) {
            if (lastDigit == digit) {
                digitCount++;
            } else {
                result.append(digitCount)//
                        .append(lastDigit);
                digitCount = 1;
                lastDigit = digit;
            }
        }

        result.append(digitCount)//
                .append(lastDigit);

        return result.toString();

    }

}
