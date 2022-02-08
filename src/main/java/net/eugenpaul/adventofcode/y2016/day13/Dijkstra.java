package net.eugenpaul.adventofcode.y2016.day13;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Dijkstra {

    @AllArgsConstructor
    @Data
    private static class Pos {
        int x;
        int y;
    }

    private Map<Pos, Integer> reachedSteps;
    private LinkedList<Pos> nextSteps;

    public Dijkstra() {
        reachedSteps = new HashMap<>();
        nextSteps = new LinkedList<>();
    }

    public int getSteps(BuildingArea area, int fromX, int fromY, int toX, int toY) {
        Pos to = new Pos(toX, toY);
        Pos from = new Pos(fromX, fromY);

        // add first step
        reachedSteps.put(from, 0);
        nextSteps.add(from);

        // pathfinding
        while (!to.equals(from)) {
            from = nextSteps.poll();
            checkAndAddNextSteps(area, from);
        }

        return reachedSteps.get(to);
    }

    public int getFieldsCount(BuildingArea area, int fromX, int fromY, int stepCount) {
        Pos from = new Pos(fromX, fromY);

        // add first step
        reachedSteps.put(from, 0);
        nextSteps.add(from);

        // pathfinding
        while (reachedSteps.get(nextSteps.getFirst()) <= stepCount) {
            from = nextSteps.poll();
            checkAndAddNextSteps(area, from);
        }

        return (int) reachedSteps.values().stream().filter(v -> v <= stepCount).count();
    }

    private void checkAndAddNextSteps(BuildingArea area, Pos src) {
        List<Pos> reachableStepsFromSrc = getNextSteps(src);
        int stepCounter = reachedSteps.get(src);

        reachableStepsFromSrc = reachableStepsFromSrc.stream()//
                .filter(v -> area.isOpenArea(v.getX(), v.getY())) // filter imposible steps out
                .filter(v -> !reachedSteps.containsKey(v)) // filter visited steps out
                .collect(Collectors.toList())//
        ;

        reachableStepsFromSrc.stream().forEach(v -> reachedSteps.put(v, stepCounter + 1));
        nextSteps.addAll(reachableStepsFromSrc);
    }

    private List<Pos> getNextSteps(Pos from) {
        List<Pos> response = new LinkedList<>();
        if (from.getX() > 0) {
            response.add(new Pos(from.getX() - 1, from.getY()));
        }
        if (from.getY() > 0) {
            response.add(new Pos(from.getX(), from.getY() - 1));
        }
        response.add(new Pos(from.getX() + 1, from.getY()));
        response.add(new Pos(from.getX(), from.getY() + 1));

        return response;
    }

}
