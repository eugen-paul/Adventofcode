package net.eugenpaul.adventofcode.y2022.day4;

import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

    @AllArgsConstructor
    private static class Range {
        private static final String FORMAT = "^(\\d*)-(\\d*)$";
        private static final Pattern PATTERN = Pattern.compile(FORMAT);

        private int from;
        private int to;

        public static Range fromString(String group) {
            Matcher m = PATTERN.matcher(group);
            if (!m.find()) {
                throw new IllegalArgumentException();
            }
            return new Range(//
                    Integer.parseInt(m.group(1)), //
                    Integer.parseInt(m.group(2))//
            );
        }

        public boolean contains(Range other) {
            return from <= other.from//
                    && to >= other.to;
        }

        public boolean isOverlap(Range other) {
            if (to < other.from) {
                return false;
            }

            return from <= other.to;
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class RangePair {
        private Range a;
        private Range b;

        private static RangePair pairFromString(String groups) {
            var g = groups.split(",");
            var group1 = Range.fromString(g[0]);
            var group2 = Range.fromString(g[1]);
            return new RangePair(group1, group2);
        }
    }

    @Getter
    private long fullyContains;
    @Getter
    private long overlapAtAll;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2022/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        fullyContains = doPuzzle1(eventData);
        overlapAtAll = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "sumOfThePriorities : " + getFullyContains());
        logger.log(Level.INFO, () -> "overlapAtAll : " + getOverlapAtAll());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        return eventData.stream()//
                .mapToInt(this::fullyContains)//
                .sum();
    }

    private int doPuzzle2(List<String> eventData) {
        return eventData.stream()//
                .mapToInt(this::overlapCount)//
                .sum();
    }

    private int fullyContains(String groups) {
        var ragnes = RangePair.pairFromString(groups);

        if (ragnes.a.contains(ragnes.b) || ragnes.b.contains(ragnes.a)) {
            return 1;
        }
        return 0;
    }

    private int overlapCount(String groups) {
        var ragnes = RangePair.pairFromString(groups);

        if (ragnes.a.isOverlap(ragnes.b)) {
            return 1;
        }
        return 0;
    }

}
