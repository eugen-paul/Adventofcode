package net.eugenpaul.adventofcode.y2016.day18;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day18 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day18.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day18.class.getName());

    @Getter
    private int safeTiles;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2016/day18/puzzle1.txt", 40);
        puzzle.doPuzzleFromFile("y2016/day18/puzzle1.txt", 400000);
    }

    public boolean doPuzzleFromFile(String filename, int rows) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData, rows);
    }

    public boolean doPuzzleFromData(String eventData, int rows) {
        if (!doEvent(eventData, rows)) {
            return false;
        }

        logger.log(Level.INFO, () -> "safeTiles " + getSafeTiles() + " in " + rows + " rows.");

        return true;
    }

    private boolean doEvent(String eventData, int rows) {
        if (null == eventData) {
            return false;
        }

        Boolean[] initRow = eventData.chars()//
                .mapToObj(c -> ((char) c) == '^')//
                .collect(Collectors.toList())//
                .toArray(new Boolean[0]);

        safeTiles = 0;
        int rowCounter = 1;

        while (rowCounter <= rows) {
            safeTiles += Stream.of(initRow)//
                    .filter(v -> !v.booleanValue())//
                    .count();
            initRow = computeNextRow(initRow);
            rowCounter++;
        }

        return true;
    }

    private Boolean[] computeNextRow(Boolean[] initRow) {
        Boolean[] response = new Boolean[initRow.length];

        for (int i = 0; i < response.length; i++) {
            response[i] = rule1(initRow, i)//
                    || rule2(initRow, i)//
                    || rule3(initRow, i)//
                    || rule4(initRow, i)//
            ;
        }

        return response;
    }

    private boolean rule1(Boolean[] initRow, int position) {
        if (position > 0 && position < initRow.length - 1) {
            return initRow[position - 1].booleanValue()//
                    && initRow[position].booleanValue()//
                    && !initRow[position + 1].booleanValue();
        }

        if (position == 0) {
            return false;
        }

        return initRow[position - 1].booleanValue()//
                && initRow[position].booleanValue()//
        ;
    }

    private boolean rule2(Boolean[] initRow, int position) {
        if (position > 0 && position < initRow.length - 1) {
            return !initRow[position - 1].booleanValue()//
                    && initRow[position].booleanValue()//
                    && initRow[position + 1].booleanValue();
        }

        if (position == 0) {
            return initRow[position].booleanValue()//
                    && initRow[position + 1].booleanValue();
        }

        return false;
    }

    private boolean rule3(Boolean[] initRow, int position) {
        if (position > 0 && position < initRow.length - 1) {
            return initRow[position - 1].booleanValue()//
                    && !initRow[position].booleanValue()//
                    && !initRow[position + 1].booleanValue();
        }

        if (position == 0) {
            return false;
        }

        return initRow[position - 1].booleanValue()//
                && !initRow[position].booleanValue()//
        ;
    }

    private boolean rule4(Boolean[] initRow, int position) {
        if (position > 0 && position < initRow.length - 1) {
            return !initRow[position - 1].booleanValue()//
                    && !initRow[position].booleanValue()//
                    && initRow[position + 1].booleanValue();
        }

        if (position == 0) {
            return !initRow[position].booleanValue()//
                    && initRow[position + 1].booleanValue();
        }

        return false;
    }

}
