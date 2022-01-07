package net.eugenpaul.adventofcode.y2015.day20;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntToLongFunction;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day20 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day20.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day20.class.getName());

    private int lowestHouseNumber;
    private int lowestHouseNumberPart2;

    public long getLowestHouseNumber() {
        return lowestHouseNumber;
    }

    public long getLowestHouseNumberPart2() {
        return lowestHouseNumberPart2;
    }

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2015/day20/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(String eventData) {
        if (!doEvent(Long.parseLong(eventData))) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "lowestHouseNumber: " + getLowestHouseNumber());
        logger.log(Level.INFO, () -> "lowestHouseNumberPart2: " + getLowestHouseNumberPart2());

        return true;
    }

    private boolean doEvent(Long eventData) {
        if (null == eventData) {
            return false;
        }

        lowestHouseNumber = doPuzzle(eventData, this::countPresentsForHouse);
        lowestHouseNumberPart2 = doPuzzle(eventData, this::countPresentsForHousePart2);

        return true;
    }

    private int doPuzzle(long presentsCount, IntToLongFunction countFunction) {
        int houseNumber = 0;
        long presents = 0;
        do {
            houseNumber++;
            presents = countFunction.applyAsLong(houseNumber);
        } while (presentsCount > presents);
        return houseNumber;
    }

    private Long countPresentsForHouse(Integer houseNumber) {
        long response = 0L;
        long limit = (long) (Math.sqrt(houseNumber));
        for (int i = 1; i <= limit; i++) {
            if (houseNumber % i == 0) {
                response += i;
                long secondMultiplier = houseNumber / i;
                if (secondMultiplier != i) {
                    response += secondMultiplier;
                }
            }
        }
        return response * 10;
    }

    private long countPresentsForHousePart2(int houseNumber) {
        long response = 0;
        long limit = (long) (Math.sqrt(houseNumber));
        for (int i = 1; i <= limit; i++) {
            if (houseNumber % i == 0) {
                long secondMultiplier = houseNumber / i;
                if (secondMultiplier <= 50) {
                    response += i;
                }
                if (secondMultiplier != i && i <= 50) {
                    response += secondMultiplier;
                }
            }
        }
        return response * 11;
    }
}
