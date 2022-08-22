package net.eugenpaul.adventofcode.y2021.day11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day11 extends SolutionTemplate {

    @Getter
    private long flashes;
    @Getter
    private long firstSynchronizing;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2021/day11/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Integer> grid = MapOfSimplePos.initMap(eventData, v -> v - '0');

        flashes = doPuzzle1(new HashMap<>(grid));
        firstSynchronizing = doPuzzle2(new HashMap<>(grid));
        logger.log(Level.INFO, () -> "flashes  : " + getFlashes());
        logger.log(Level.INFO, () -> "firstSynchronizing  : " + getFirstSynchronizing());

        return true;
    }

    private int doPuzzle1(Map<SimplePos, Integer> grid) {
        int flashesTotal = 0;
        for (int i = 0; i < 100; i++) {
            Set<SimplePos> flashesThisStep = new HashSet<>();
            for (var pos : grid.keySet()) {
                flashesTotal += doStep(grid, pos, flashesThisStep);
            }
            for (var pos : flashesThisStep) {
                grid.put(pos, 0);
            }
        }

        return flashesTotal;
    }

    private int doPuzzle2(Map<SimplePos, Integer> grid) {
        int step = 0;
        while (true) {
            step++;
            Set<SimplePos> flashesThisStep = new HashSet<>();
            for (var pos : grid.keySet()) {
                doStep(grid, pos, flashesThisStep);
            }
            if (flashesThisStep.size() == grid.size()) {
                return step;
            }
            for (var pos : flashesThisStep) {
                grid.put(pos, 0);
            }
        }
    }

    private int doStep(Map<SimplePos, Integer> grid, SimplePos pos, Set<SimplePos> flashesThisStep) {
        if (flashesThisStep.contains(pos) || !grid.containsKey(pos)) {
            return 0;
        }
        int response = 0;
        Integer value = grid.compute(pos, (k, v) -> v + 1);
        if (value > 9) {
            response++;
            flashesThisStep.add(pos);
            var neighbors = pos.getNeighbors(true);
            for (var neighbor : neighbors) {
                response += doStep(grid, neighbor, flashesThisStep);
            }
        }
        return response;
    }

}
