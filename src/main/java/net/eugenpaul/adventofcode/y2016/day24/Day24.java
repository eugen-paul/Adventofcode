package net.eugenpaul.adventofcode.y2016.day24;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Permutation;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day24 extends SolutionTemplate {

    private int shortestRouteTemp;

    @Getter
    private int shortestRoute;

    @Getter
    private int shortestRoute2;

    private DuctLayout layout;

    private boolean returnToStart;

    private Map<SimplePos, Map<SimplePos, Integer>> cache;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2016/day24/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        cache = new HashMap<>();

        layout = DuctLayout.fromStringList(eventData);

        // Puzzle 1
        shortestRoute = duPuzzle(false);

        // Puzzle 2
        shortestRoute2 = duPuzzle(true);

        logger.log(Level.INFO, () -> "shortestRoute " + getShortestRoute());
        logger.log(Level.INFO, () -> "shortestRoute2 " + getShortestRoute2());

        return true;
    }

    private int duPuzzle(boolean returnToZero) {
        int maxNumber = 0;
        while (layout.getNumberPosition(maxNumber) != null) {
            maxNumber++;
        }

        shortestRouteTemp = Integer.MAX_VALUE;
        returnToStart = returnToZero;
        List<Integer> numbers = new LinkedList<>();
        for (int i = 1; i < maxNumber; i++) {
            numbers.add(i);
        }

        Permutation.permutationsRecursive(numbers.size(), numbers.toArray(new Integer[0]), this::computeDistanceOfPermutation);
        return shortestRouteTemp;
    }

    private void computeDistanceOfPermutation(Integer[] input) {
        var startPos = layout.getNumberPosition(0);

        int route = 0;
        for (Integer c : input) {
            Dijkstra pathfinding = new Dijkstra(layout);
            var endPos = layout.getNumberPosition(c);
            var fromPos = startPos;

            route += cache.computeIfAbsent(startPos, key -> {
                Map<SimplePos, Integer> value = new HashMap<>();
                value.put(endPos, pathfinding.getSteps(fromPos.getX(), fromPos.getY(), endPos.getX(), endPos.getY()));
                return value;
            }).computeIfAbsent(endPos, key -> pathfinding.getSteps(fromPos.getX(), fromPos.getY(), endPos.getX(), endPos.getY()));

            startPos = endPos;
        }

        if (returnToStart) {
            Dijkstra pathfinding = new Dijkstra(layout);
            route += pathfinding.getSteps(startPos.getX(), startPos.getY(), layout.getNumberPosition(0).getX(), layout.getNumberPosition(0).getY());
        }

        shortestRouteTemp = Math.min(shortestRouteTemp, route);
    }

}
