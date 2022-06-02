package net.eugenpaul.adventofcode.y2016.day18;

import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day18 extends SolutionTemplate {

    @Getter
    private int safeTiles;

    @Setter
    private int rows;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.rows = 40;
        puzzle.doPuzzleFromFile("y2016/day18/puzzle1.txt");
        puzzle.rows = 400000;
        puzzle.doPuzzleFromFile("y2016/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
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

        logger.log(Level.INFO, () -> "safeTiles " + getSafeTiles() + " in " + rows + " rows.");

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
