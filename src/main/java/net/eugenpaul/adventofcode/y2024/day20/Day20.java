package net.eugenpaul.adventofcode.y2024.day20;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day20 extends SolutionTemplate {

    private record StepCost(SimplePos pos, long cost) {

    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @Setter
    private int minSave = 100;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2024/day20/puzzle1.txt");
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

        // response = slow(eventData, response);
        response = fast(eventData, response, 2);

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long slow(List<String> eventData, long response) {
        Set<SimplePos> m = StringConverter.toSet(eventData, '.', 'S', 'E');
        SimplePos start = StringConverter.posOfChar(eventData, 'S');
        SimplePos end = StringConverter.posOfChar(eventData, 'E');

        int maxX = eventData.get(0).length() - 1;
        int maxY = eventData.size() - 1;

        long bestCost = cost(m, start, end);

        for (int x = 1; x < maxX; x++) {
            for (int y = 1; y < maxY; y++) {
                var p = new SimplePos(x, y);
                if (m.contains(p)) {
                    continue;
                }
                m.add(p);
                if (bestCost - cost(m, start, end) >= minSave) {
                    response++;
                }
                m.remove(p);
            }
        }
        return response;
    }

    private long cost(Set<SimplePos> m, SimplePos start, SimplePos end) {
        Set<SimplePos> visited = new HashSet<>();
        visited.add(start);

        LinkedList<StepCost> toCheck = new LinkedList<>();
        toCheck.add(new StepCost(start, 0));

        while (!toCheck.isEmpty()) {
            var cur = toCheck.pollFirst();
            if (cur.pos.equals(end)) {
                return cur.cost;
            }
            for (var nb : cur.pos.getNeighbors(false)) {
                if (visited.contains(nb) || !m.contains(nb)) {
                    continue;
                }
                visited.add(nb);
                toCheck.add(new StepCost(nb, cur.cost + 1));
            }
        }
        throw new IllegalArgumentException();
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        response = fast(eventData, response, 20);

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long fast(List<String> eventData, long response, int cheatLen) {
        Set<SimplePos> m = StringConverter.toSet(eventData, '.', 'S', 'E');
        Set<SimplePos> mRev = StringConverter.toSet(eventData, '#');
        SimplePos start = StringConverter.posOfChar(eventData, 'S');
        SimplePos end = StringConverter.posOfChar(eventData, 'E');

        int maxX = eventData.get(0).length() - 1;
        int maxY = eventData.size() - 1;

        Map<SimplePos, Integer> visited = new HashMap<>();
        visited.put(start, 0);

        LinkedList<SimplePos> toCheck = new LinkedList<>();
        toCheck.add(start);

        while (!toCheck.isEmpty()) {
            var cur = toCheck.pollFirst();
            if (cur.equals(end)) {
                break;
            }
            var curCost = visited.get(cur);
            for (var nb : cur.getNeighbors(false)) {
                if (visited.containsKey(nb) || !m.contains(nb)) {
                    continue;
                }
                visited.put(nb, curCost + 1);
                toCheck.add(nb);
            }
        }

        for (var startPos : m) {
            for (int x = Math.max(1, startPos.getX() - cheatLen); x <= Math.min(maxX, startPos.getX() + cheatLen); x++) {
                for (int y = Math.max(1, startPos.getY() - cheatLen); y <= Math.min(maxY, startPos.getY() + cheatLen); y++) {
                    SimplePos endPos = new SimplePos(x, y);
                    if (mRev.contains(endPos)) {
                        continue;
                    }
                    int cEnd = visited.get(endPos);
                    int cStart = visited.get(startPos);
                    int oldDelta = cEnd - cStart;
                    int md = (int) startPos.manhattanDistance(endPos);
                    if (md > cheatLen) {
                        continue;
                    }
                    long totalSaveCost = oldDelta - md;
                    if (totalSaveCost >= minSave) {
                        response++;
                    }
                }
            }
        }
        return response;
    }

}
