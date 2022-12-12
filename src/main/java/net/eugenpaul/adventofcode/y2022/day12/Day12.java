package net.eugenpaul.adventofcode.y2022.day12;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.Dijkstra;
import net.eugenpaul.adventofcode.helper.dijkstra.Maze;

public class Day12 extends SolutionTemplate {

    @AllArgsConstructor
    private class MazeDay12 implements Maze {

        Map<SimplePos, Integer> area;

        @Override
        public List<SimplePos> getNextSteps(SimplePos from) {
            List<SimplePos> steps = new LinkedList<>();

            int currentH = area.get(from);

            for (var d : Direction.values()) {
                var to = from.moveNew(d);
                var toH = area.get(to);
                if (toH != null && toH - currentH <= 1) {
                    steps.add(to);
                }
            }

            return steps;
        }

    }

    @Getter
    private long stepsPart1;
    @Getter
    private long stepsPart2;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2022/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        stepsPart1 = doPuzzle1(eventData);
        stepsPart2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "stepsPart1 : " + getStepsPart1());
        logger.log(Level.INFO, () -> "stepsPart2 : " + getStepsPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        SimplePos startPos = new SimplePos(0, 0);
        SimplePos endPos = new SimplePos(0, 0);
        Map<SimplePos, Integer> area = initMaze(eventData, startPos, endPos);

        var maze = new MazeDay12(area);

        Dijkstra algo = new Dijkstra();

        return algo.getSteps(maze, startPos, endPos);
    }

    private long doPuzzle2(List<String> eventData) {
        SimplePos startPos = new SimplePos(0, 0);
        SimplePos endPos = new SimplePos(0, 0);
        Map<SimplePos, Integer> area = initMaze(eventData, startPos, endPos);

        int responsePath = Integer.MAX_VALUE;

        for (var areaPoint : area.entrySet()) {
            if (areaPoint.getValue() != 'a') {
                continue;
            }
            var maze = new MazeDay12(area);
            Dijkstra algo = new Dijkstra();
            var steps = algo.getSteps(maze, areaPoint.getKey().copy(), endPos);
            if (steps != null) {
                responsePath = Math.min(responsePath, steps);
            }
        }

        return responsePath;
    }

    private Map<SimplePos, Integer> initMaze(List<String> eventData, SimplePos startPos, SimplePos endPos) {
        Map<SimplePos, Integer> area = MapOfSimplePos.initMap(eventData, v -> v.charValue() + 0);

        var start = area.entrySet().stream().filter(v -> v.getValue() == 'S' + 0).findAny().orElseThrow();
        var end = area.entrySet().stream().filter(v -> v.getValue() == 'E' + 0).findAny().orElseThrow();

        startPos.add(start.getKey());
        endPos.add(end.getKey());

        area.put(start.getKey(), 'a' + 0);
        area.put(end.getKey(), 'z' + 0);

        return area;
    }

}
