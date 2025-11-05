package net.eugenpaul.adventofcode.y2024.day18;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day18 extends SolutionTemplate {

    private record Step(SimplePos pos, int cost) {
    }

    @Getter
    private long totalScore;
    @Getter
    private String totalScore2;

    @Setter
    private int size = 71;
    @Setter
    private int bytes1 = 1024;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2024/day18/puzzle1.txt");
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

        Set<SimplePos> m = new HashSet<>();
        for (int i = 0; i < bytes1; i++) {
            m.add(SimplePos.fromData(eventData.get(i), ","));
        }

        SimplePos start = new SimplePos(0, 0);
        SimplePos end = new SimplePos(size - 1, size - 1);

        Set<SimplePos> visited = new HashSet<>();
        visited.add(start);

        LinkedList<Step> toCheck = new LinkedList<>();
        toCheck.add(new Step(start, 0));

        while (!toCheck.isEmpty()) {
            var cur = toCheck.pollFirst();
            if (cur.pos.equals(end)) {
                response = cur.cost;
                break;
            }

            for (var nb : cur.pos.getNeighbors(false)) {
                if (!nb.inRange(0, size - 1, 0, size - 1)) {
                    continue;
                }
                if (visited.contains(nb) || m.contains(nb)) {
                    continue;
                }
                visited.add(nb);
                toCheck.add(new Step(nb, cur.cost + 1));
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public String doPuzzle2(List<String> eventData) {
        String response = "";

        Set<SimplePos> m = new HashSet<>();

        // Alternative / faster solution => use UnionFind and check when walls belong to the same root.
        for (String data : eventData) {
            m.add(SimplePos.fromData(data, ","));
            if (!isOk(m)) {
                response = data;
                break;
            }
        }
        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private boolean isOk(Set<SimplePos> m) {
        SimplePos start = new SimplePos(0, 0);
        SimplePos end = new SimplePos(size - 1, size - 1);

        Set<SimplePos> visited = new HashSet<>();
        visited.add(start);

        LinkedList<Step> toCheck = new LinkedList<>();
        toCheck.add(new Step(start, 0));

        while (!toCheck.isEmpty()) {
            var cur = toCheck.pollFirst();
            if (cur.pos.equals(end)) {
                return true;
            }

            for (var nb : cur.pos.getNeighbors(false)) {
                if (!nb.inRange(0, size - 1, 0, size - 1)) {
                    continue;
                }
                if (visited.contains(nb) || m.contains(nb)) {
                    continue;
                }
                visited.add(nb);
                toCheck.add(new Step(nb, cur.cost + 1));
            }
        }
        return false;
    }

}
