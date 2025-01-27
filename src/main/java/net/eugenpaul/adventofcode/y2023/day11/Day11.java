package net.eugenpaul.adventofcode.y2023.day11;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day11 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;
    @Setter
    private long exp = 1_000_000;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2023/day11/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;
        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        Set<SimplePos> m = StringConverter.toSet(eventData, '#');
        Set<Integer> emptyCollumns = new HashSet<>();
        Set<Integer> emptyRows = new HashSet<>();
        initEmpties(m, emptyCollumns, emptyRows, maxX, maxY);

        response = compureDist(m, emptyCollumns, emptyRows, 2);

        logger.info("Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        Set<SimplePos> m = StringConverter.toSet(eventData, '#');
        Set<Integer> emptyCollumns = new HashSet<>();
        Set<Integer> emptyRows = new HashSet<>();
        initEmpties(m, emptyCollumns, emptyRows, maxX, maxY);

        response = compureDist(m, emptyCollumns, emptyRows, exp);

        logger.info("Solution 2 " + response);
        return response;
    }

    private void initEmpties(Set<SimplePos> m, Set<Integer> emptyCollumns, Set<Integer> emptyRows, int maxX, int maxY) {
        for (int i = 0; i < maxX; i++) {
            emptyCollumns.add(i);
        }
        for (int i = 0; i < maxY; i++) {
            emptyRows.add(i);
        }
        for (var galaxy : m) {
            emptyCollumns.remove(galaxy.getX());
            emptyRows.remove(galaxy.getY());
        }
    }

    private long compureDist(Set<SimplePos> m, Set<Integer> emptyCollumns, Set<Integer> emptyRows, long emptySpace) {
        long response = 0L;
        for (SimplePos start : m) {
            for (SimplePos end : m) {
                int xS = Math.min(start.getX(), end.getX());
                int xE = Math.max(start.getX(), end.getX());
                int yS = Math.min(start.getY(), end.getY());
                int yE = Math.max(start.getY(), end.getY());
                response += xE - xS + yE - yS;
                for (Integer col : emptyCollumns) {
                    if (xS < col && col < xE) {
                        response += emptySpace - 1;
                    }
                }
                for (Integer row : emptyRows) {
                    if (yS < row && row < yE) {
                        response += emptySpace - 1;
                    }
                }
            }
        }
        return response / 2L;
    }

}
