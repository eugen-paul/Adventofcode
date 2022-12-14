package net.eugenpaul.adventofcode.y2022.day4;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Range;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

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

        logger.log(Level.INFO, () -> "fullyContains : " + getFullyContains());
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
        var ragnes = groups.split(",");

        var rangeA = Range.fromString(ragnes[0], "-");
        var rangeB = Range.fromString(ragnes[1], "-");
        if (rangeA.isContain(rangeB) || rangeB.isContain(rangeA)) {
            return 1;
        }
        return 0;
    }

    private int overlapCount(String groups) {
        var ragnes = groups.split(",");

        var rangeA = Range.fromString(ragnes[0], "-");
        var rangeB = Range.fromString(ragnes[1], "-");
        if (rangeA.isOverlap(rangeB)) {
            return 1;
        }
        return 0;
    }

}
