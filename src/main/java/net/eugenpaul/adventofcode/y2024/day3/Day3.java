package net.eugenpaul.adventofcode.y2024.day3;

import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private static final String PATTERN = "mul\\(\\d{1,3},\\d{1,3}\\)";
    private static final Pattern PATTERN_COMPILED = Pattern.compile(PATTERN);

    private static final String PATTERN2 = "mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\)";
    private static final Pattern PATTERN2_COMPILED = Pattern.compile(PATTERN2);

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2024/day3/puzzle1.txt");
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
            var matcher = PATTERN_COMPILED.matcher(line);
            while (matcher.find()) {
                String match = matcher.group();
                String[] parts = match.substring(4, match.length() - 1).split(",");
                long a = Long.parseLong(parts[0]);
                long b = Long.parseLong(parts[1]);
                response += a * b;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        boolean doIt = true;

        for (var line : eventData) {
            var matcher = PATTERN2_COMPILED.matcher(line);
            while (matcher.find()) {
                String match = matcher.group();
                if (match.startsWith("do(")) {
                    doIt = true;
                    continue;
                }
                if (match.startsWith("don't(")) {
                    doIt = false;
                    continue;
                }
                if (!doIt) {
                    continue;
                }
                String[] parts = match.substring(4, match.length() - 1).split(",");
                long a = Long.parseLong(parts[0]);
                long b = Long.parseLong(parts[1]);
                response += a * b;
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
