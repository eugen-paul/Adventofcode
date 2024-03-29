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

    public Integer getSteps(Maze area, SimplePos from, SimplePos to) {
        SimplePos checkPosition = from;
        // set first Position as reached
        reachedSteps.put(checkPosition, 0);

        // pathfinding
        while (checkPosition != null && !to.equals(checkPosition)) {
            checkAndAddNextSteps(area, checkPosition);
            checkPosition = nextSteps.poll();
        }

        return reachedSteps.get(to);
    }

    public Integer getSteps(Maze area, int fromX, int fromY, int toX, int toY) {
        SimplePos from = new SimplePos(fromX, fromY);
        SimplePos to = new SimplePos(toX, toY);

        return getSteps(area, from, to);
    }

    public int getReachableFieldsCount(Maze area, int fromX, int fromY, int stepCount) {
        SimplePos from = new SimplePos(fromX, fromY);

        // set first Position as reached
        reachedSteps.put(from, 0);

        // pathfinding
        while (from != null && reachedSteps.get(from) <= stepCount) {
            checkAndAddNextSteps(area, from);
            from = nextSteps.poll();
        }

        return (int) reachedSteps.values().stream().filter(v -> v <= stepCount).count();
    }

    public Map<SimplePos, Integer> getReachableFields(Maze area, int fromX, int fromY) {
        SimplePos from = new SimplePos(fromX, fromY);

        return getReachableFields(area, from);
    }

    public Map<SimplePos, Integer> getReachableFields(Maze area, SimplePos from) {
        // set first Position as reached
        reachedSteps.put(from, 0);

        // pathfinding
        while (from != null) {
            checkAndAddNextSteps(area, from);
            from = nextSteps.poll();
        }

        return new HashMap<>(reachedSteps);
    }

    public int getFullSpreadTime(Maze areaOrigin, SimplePos start) {
        int responseSpreadTime = 0;

        reachedSteps.put(start, 0);

        checkAndAddNextSteps(areaOrigin, start);

        while (!nextSteps.isEmpty()) {
            LinkedList<SimplePos> nextStepsToCheck = new LinkedList<>(nextSteps);
            nextSteps.clear();

            for (SimplePos nextStep : nextStepsToCheck) {
                checkAndAddNextSteps(areaOrigin, nextStep);
            }

            responseSpreadTime++;
        }

        return responseSpreadTime;
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
