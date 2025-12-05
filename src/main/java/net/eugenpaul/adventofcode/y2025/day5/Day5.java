package net.eugenpaul.adventofcode.y2025.day5;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Range;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2025/day5/puzzle1.txt");
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

        var ll = asListList(eventData);

        List<Range> ranges = new ArrayList<>();
        for (var r : ll.get(0)) {
            ranges.add(Range.fromString(r, "-"));
        }

        for (var ing : ll.get(1)) {
            var i = toLong(ing);
            if (ranges.stream().anyMatch(v -> v.contain(i))) {
                response++;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        var ll = asListList(eventData);

        List<Range> ranges = new ArrayList<>();
        for (var r : ll.get(0)) {
            ranges.add(Range.fromString(r, "-"));
        }

        List<Range> all = new ArrayList<>();
        for (var r : ranges) {
            all = Range.merge(all, r);
        }

        response = all.stream().mapToLong(v -> v.getTo() - v.getFrom() + 1).sum();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
