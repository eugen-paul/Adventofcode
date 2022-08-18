package net.eugenpaul.adventofcode.y2021.day9;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.Maze;
import net.eugenpaul.adventofcode.helper.dijkstra.Dijkstra;

public class Day9 extends SolutionTemplate {

    @Getter
    private long sum;
    @Getter
    private long mult;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2021/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Integer> area = MapOfSimplePos.initMap(eventData, v -> Integer.parseInt(v.toString()));

        sum = doPuzzle1(area);
        mult = doPuzzle2(area);

        logger.log(Level.INFO, () -> "sum  : " + getSum());
        logger.log(Level.INFO, () -> "mult : " + getMult());

        return true;
    }

    private int doPuzzle1(Map<SimplePos, Integer> area) {
        var mapOfLowPoints = getLowPoints(area);
        return mapOfLowPoints.values().stream().reduce(0, (a, b) -> a + b + 1);
    }

    private int doPuzzle2(Map<SimplePos, Integer> area) {
        var mapOfLowPoints = getLowPoints(area);

        Maze maze = from -> {
            var neighbors = from.getNeighbors(false);
            neighbors.removeIf(v -> area.getOrDefault(v, 9) == 9);
            return neighbors;
        };

        List<Integer> sizeOfBasin = mapOfLowPoints.keySet().stream()//
                .mapToInt(v -> new Dijkstra().getReachableFields(maze, v).size())//
                .boxed()//
                .sorted((a, b) -> b - a)//
                .collect(Collectors.toList());

        return sizeOfBasin.get(0) * sizeOfBasin.get(1) * sizeOfBasin.get(2);
    }

    private Map<SimplePos, Integer> getLowPoints(Map<SimplePos, Integer> area) {
        Map<SimplePos, Integer> mapOfLowPoints = new HashMap<>();
        for (var entry : area.entrySet()) {
            var neighbors = entry.getKey().getNeighbors(false);
            if (neighbors.stream().allMatch(v -> area.getOrDefault(v, 10) > entry.getValue())) {
                mapOfLowPoints.put(entry.getKey(), entry.getValue());
            }
        }
        return mapOfLowPoints;
    }
}
