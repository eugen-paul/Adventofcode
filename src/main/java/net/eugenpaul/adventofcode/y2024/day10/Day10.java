package net.eugenpaul.adventofcode.y2024.day10;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day10 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2024/day10/puzzle1.txt");
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

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        response = m.entrySet().stream().mapToLong(v -> getScore(m, v.getKey())).sum();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private int getScore(Map<SimplePos, Character> m, SimplePos pos) {
        if (m.get(pos) != '0') {
            return 0;
        }

        SimplePos p = pos;
        Set<SimplePos> visited = new HashSet<>();
        Set<SimplePos> target = new HashSet<>();
        LinkedList<SimplePos> toCheck = new LinkedList<>();
        toCheck.add(p);
        while (!toCheck.isEmpty()) {
            SimplePos cur = toCheck.pollFirst();
            Character c = m.get(cur);
            if (c == '9') {
                target.add(cur);
                continue;
            }
            for (var nb : cur.getNeighbors(false)) {
                if (m.getOrDefault(nb, 'G') == c + 1 && !visited.contains(nb)) {
                    toCheck.add(nb);
                    visited.add(nb);
                }
            }
        }
        return target.size();
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        response = m.entrySet().stream().mapToLong(v -> getScore2(m, v.getKey())).sum();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private int getScore2(Map<SimplePos, Character> m, SimplePos pos) {
        if (m.get(pos) != '0') {
            return 0;
        }

        SimplePos p = pos;
        int target = 0;
        LinkedList<SimplePos> toCheck = new LinkedList<>();
        toCheck.add(p);
        while (!toCheck.isEmpty()) {
            SimplePos cur = toCheck.pollFirst();
            Character c = m.get(cur);
            if (c == '9') {
                target++;
                continue;
            }
            for (var nb : cur.getNeighbors(false)) {
                if (m.getOrDefault(nb, 'G') == c + 1) {
                    toCheck.add(nb);
                }
            }
        }
        return target;
    }

}
