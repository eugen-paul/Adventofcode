package net.eugenpaul.adventofcode.y2025.day4;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day4 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2025/day4/puzzle1.txt");
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

        Set<SimplePos> m = StringConverter.toSet(eventData, '@');

        for (var p : m) {
            long c = p.getNeighbors(true).stream().filter(m::contains).count();
            if (c < 4) {
                response++;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        Set<SimplePos> m = StringConverter.toSet(eventData, '@');

        for (var p : m) {
            int c = 0;
            for (var nb : p.getNeighbors(true)) {
                if (m.contains(nb)) {
                    c++;
                }
            }
            if (c < 4) {
                response++;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Set<SimplePos> m = StringConverter.toSet(eventData, '@');
        Set<SimplePos> removed;

        while (true) {
            removed = remove(m);
            if (removed.isEmpty()) {
                break;
            }
            response += removed.size();
            m.removeAll(removed);
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private Set<SimplePos> remove(Set<SimplePos> m) {
        Set<SimplePos> toRemove = new HashSet<>();
        for (var p : m) {
            int c = 0;
            for (var nb : p.getNeighbors(true)) {
                if (m.contains(nb)) {
                    c++;
                }
            }
            if (c < 4) {
                toRemove.add(p);
            }
        }
        return toRemove;
    }

}
