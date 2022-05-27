package net.eugenpaul.adventofcode.helper.dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.eugenpaul.adventofcode.helper.SimplePos;

public class Dijkstra {

    private Map<SimplePos, Integer> reachedSteps;
    private LinkedList<SimplePos> nextSteps;

    public Dijkstra() {
        reachedSteps = new HashMap<>();
        nextSteps = new LinkedList<>();
    }

    public int getSteps(Maze area, SimplePos from, SimplePos to) {
        // set first Position as reached
        reachedSteps.put(from, 0);

        // pathfinding
        while (!to.equals(from)) {
            checkAndAddNextSteps(area, from);
            from = nextSteps.poll();
        }

        return reachedSteps.get(to);
    }

    public int getSteps(Maze area, int fromX, int fromY, int toX, int toY) {
        SimplePos from = new SimplePos(fromX, fromY);
        SimplePos to = new SimplePos(toX, toY);

        return getSteps(area, from, to);
    }

    public int getReachableFieldsCount(Maze area, int fromX, int fromY, int stepCount) {
        SimplePos from = new SimplePos(fromX, fromY);

        // set first Position as reached
        reachedSteps.put(from, 0);

        // pathfinding
        while (reachedSteps.get(from) <= stepCount) {
            checkAndAddNextSteps(area, from);
            from = nextSteps.poll();
        }

        return (int) reachedSteps.values().stream().filter(v -> v <= stepCount).count();
    }

    private void checkAndAddNextSteps(Maze area, SimplePos src) {
        List<SimplePos> reachableStepsFromSrc = area.getNextSteps(src);
        int stepCounter = reachedSteps.get(src);

        reachableStepsFromSrc = reachableStepsFromSrc.stream()//
                .filter(v -> !reachedSteps.containsKey(v)) // filter visited steps out
                .collect(Collectors.toList())//
        ;

        reachableStepsFromSrc.stream().forEach(v -> reachedSteps.put(v, stepCounter + 1));
        nextSteps.addAll(reachableStepsFromSrc);
    }

}
