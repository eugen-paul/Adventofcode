package net.eugenpaul.adventofcode.helper.dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DijkstraGen {

    @AllArgsConstructor
    private static class Path<T extends DijkstraStepGen> {
        private int totalCost;
        private LinkedList<T> p;
    }

    public static <T extends DijkstraStepGen> Integer getSteps(MazeGen<T> area, T from, T to) {
        Map<String, Integer> reachedSteps = new HashMap<>();

        TreeMap<Integer, List<T>> nextStepMap = new TreeMap<>();

        T checkPosition = from;
        // set first Position as reached
        reachedSteps.put(checkPosition.compHash(), 0);

        // pathfinding
        while (checkPosition != null && !to.compHash().equals(checkPosition.compHash())) {
            checkAndAddNextSteps(area, checkPosition, reachedSteps, nextStepMap);
            var first = nextStepMap.firstEntry();
            if (first != null) {
                checkPosition = first.getValue().remove(0);
                if (first.getValue().isEmpty()) {
                    nextStepMap.remove(first.getKey());
                }
            } else {
                checkPosition = null;
            }
        }

        return reachedSteps.get(to.compHash());
    }

    private static <T extends DijkstraStepGen> void checkAndAddNextSteps( //
            MazeGen<T> area, //
            T src, //
            Map<String, Integer> reachedSteps, //
            TreeMap<Integer, List<T>> nextStepMap //
    ) {
        List<T> reachableStepsFromSrc = area.getNextSteps(src);
        int stepCounter = reachedSteps.get(src.compHash());

        reachableStepsFromSrc = reachableStepsFromSrc.stream()//
                .filter(v -> {
                    Integer oldCost = reachedSteps.get(v.compHash());
                    return oldCost == null || oldCost > stepCounter + v.getCost();
                }) // filter visited steps out
                .collect(Collectors.toList())//
        ;

        reachableStepsFromSrc.stream().forEach(v -> reachedSteps.put(v.compHash(), stepCounter + v.getCost()));
        reachableStepsFromSrc.stream().forEach(v -> nextStepMap.computeIfAbsent(stepCounter + v.getCost(), k -> new LinkedList<>()).add(v));
    }

    public static <T extends DijkstraStepGen> List<T> getPath(MazeGen<T> area, T from, T to) {
        Map<String, Path<T>> reachedSteps = new HashMap<>();

        TreeMap<Integer, List<T>> nextStepMap = new TreeMap<>();

        T checkPosition = from;
        // set first Position as reached
        reachedSteps.put(checkPosition.compHash(), new Path<>(0, new LinkedList<>()));

        // pathfinding
        while (checkPosition != null && !to.compHash().equals(checkPosition.compHash())) {
            checkAndAddNextStepsWithPath(area, checkPosition, reachedSteps, nextStepMap);
            var first = nextStepMap.firstEntry();
            if (first != null) {
                checkPosition = first.getValue().remove(0);
                if (first.getValue().isEmpty()) {
                    nextStepMap.remove(first.getKey());
                }
            } else {
                checkPosition = null;
            }
        }

        return reachedSteps.get(to.compHash()).p;
    }

    private static <T extends DijkstraStepGen> void checkAndAddNextStepsWithPath( //
            MazeGen<T> area, //
            T src, //
            Map<String, Path<T>> reachedSteps, //
            TreeMap<Integer, List<T>> nextStepMap //
    ) {
        List<T> reachableStepsFromSrc = area.getNextSteps(src);
        Path<T> currentPath = reachedSteps.get(src.compHash());

        reachableStepsFromSrc = reachableStepsFromSrc.stream()//
                .filter(v -> {
                    Path<T> oldCost = reachedSteps.get(v.compHash());
                    return oldCost == null || oldCost.totalCost > currentPath.totalCost + v.getCost();
                }) // filter visited steps out
                .collect(Collectors.toList())//
        ;

        reachableStepsFromSrc.stream()//
                .forEach(v -> {
                    LinkedList<T> newPath = new LinkedList<>(currentPath.p);
                    newPath.add(v);
                    reachedSteps.put(v.compHash(), new Path<>(currentPath.totalCost + v.getCost(), newPath));
                });

        reachableStepsFromSrc.stream()//
                .forEach(v -> nextStepMap.computeIfAbsent(currentPath.totalCost + v.getCost(), k -> new LinkedList<>()).add(v));
    }

}
