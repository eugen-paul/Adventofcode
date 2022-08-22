package net.eugenpaul.adventofcode.y2021.day12;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.logging.Level;

import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day12 extends SolutionTemplate {

    @Data
    private class Path {
        private LinkedList<String> p;
        private boolean smallCaveVisitedTwice;

        public Path(String startCave) {
            p = new LinkedList<>();
            p.add(startCave);
            smallCaveVisitedTwice = false;
        }

        private Path() {
            p = new LinkedList<>();
            smallCaveVisitedTwice = false;
        }

        public Path genPath(String nextCave) {
            Path responsePath = new Path();

            responsePath.p.addAll(p);
            responsePath.smallCaveVisitedTwice = smallCaveVisitedTwice;

            if (responsePath.p.contains(nextCave) && !isBigCave(nextCave)) {
                responsePath.smallCaveVisitedTwice = true;
            }
            responsePath.p.add(nextCave);
            return responsePath;
        }

        public String getNextStep() {
            return p.getLast();
        }

    }

    @Getter
    private long paths1;
    @Getter
    private long paths2;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2021/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var grid = initGrid(eventData);

        paths1 = getAllPathsCount(grid, this::canBeVisit1).size();
        paths2 = getAllPathsCount(grid, this::canBeVisit2).size();

        logger.log(Level.INFO, () -> "paths1  : " + getPaths1());
        logger.log(Level.INFO, () -> "paths2  : " + getPaths2());

        return true;
    }

    private Map<String, List<String>> initGrid(List<String> eventData) {
        Map<String, List<String>> grid = new HashMap<>();
        for (var data : eventData) {
            var points = data.split("-");
            grid.computeIfAbsent(points[0], v -> new LinkedList<>())//
                    .add(points[1]);
            grid.computeIfAbsent(points[1], v -> new LinkedList<>())//
                    .add(points[0]);
        }
        return grid;
    }

    private LinkedList<Path> getAllPathsCount(Map<String, List<String>> grid, BiPredicate<Path, String> checkFunction) {
        LinkedList<Path> stepsToCheck = new LinkedList<>();
        LinkedList<Path> allPathList = new LinkedList<>();

        stepsToCheck.add(new Path("start"));

        while (!stepsToCheck.isEmpty()) {
            var currentPath = stepsToCheck.removeFirst();

            var currentCave = currentPath.getNextStep();
            if (currentCave.equals("end")) {
                allPathList.add(currentPath);
            } else {
                var nextPossibleSteps = grid.get(currentCave);
                for (var checkCave : nextPossibleSteps) {
                    if (!checkFunction.test(currentPath, checkCave)) {
                        // don't visit small caves more than once
                        continue;
                    }
                    var newStep = currentPath.genPath(checkCave);
                    stepsToCheck.add(newStep);
                }
            }
        }

        return allPathList;
    }

    private boolean canBeVisit1(Path step, String nextCave) {
        return !step.p.contains(nextCave) || isBigCave(nextCave);
    }

    private boolean canBeVisit2(Path step, String nextCave) {
        if (isBigCave(nextCave)) {
            return true;
        }

        if (nextCave.equals("start")) {
            return false;
        }

        long i = step.p.stream().filter(v -> v.equals(nextCave)).count();
        return (!step.smallCaveVisitedTwice && i <= 1L) || (step.smallCaveVisitedTwice && i == 0L);
    }

    private boolean isBigCave(String cave) {
        return cave.charAt(0) >= 'A' && cave.charAt(0) <= 'Z';
    }
}
