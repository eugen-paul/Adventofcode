package net.eugenpaul.adventofcode.y2024.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day2 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2024/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        for (var line : eventData) {
            List<Integer> report = StringConverter.toIntegerArrayList(line);
            if (isSafe(report)) {
                response++;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private boolean isSafe(List<Integer> report) {
        int d = report.get(1) - report.get(0);
        if (d == 0 || Math.abs(d) > 3) {
            return false;
        }

        boolean up = d > 0;
        for (int i = 2; i < report.size(); i++) {
            int c = report.get(i) - report.get(i - 1);
            if (c == 0 || Math.abs(c) > 3 || (c > 0) != up) {
                return false;
            }
        }
        return true;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        for (var line : eventData) {
            List<Integer> report = StringConverter.toIntegerArrayList(line);
            if (isSafe(report)) {
                response++;
            } else {
                for (int i = 0; i < report.size(); i++) {
                    List<Integer> reportCopy = new ArrayList<>(report);
                    reportCopy.remove(i);
                    if (isSafe(reportCopy)) {
                        response++;
                        break;
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
