package net.eugenpaul.adventofcode.y2019.day15;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;
import net.eugenpaul.adventofcode.helper.computer.IntcodeMapComputer;
import net.eugenpaul.adventofcode.helper.dijkstra.Dijkstra;
import net.eugenpaul.adventofcode.helper.dijkstra.Maze;

public class Day15 extends SolutionTemplate {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private enum AreaData {
        UNKNOW(' '), EMPTY('.'), WALL('#'), VISITED('!'), OXYGEN('O');

        @Getter
        private final char value;
    }

    private int pos = 0;
    private IntcodeMapComputer comp;
    private Map<Long, Long> opcodes;

    @Getter
    private long stepsToOxygen;
    @Getter
    private long fullSpreadTime;

    @AllArgsConstructor
    private class SimpleMaze implements Maze {
        Map<SimplePos, AreaData> area;

        @Override
        public List<SimplePos> getNextSteps(SimplePos from) {
            List<SimplePos> response = new LinkedList<>();

            for (Direction direction : Direction.values()) {
                var dirStep = area.getOrDefault(from.moveNew(direction), AreaData.UNKNOW);
                if (dirStep == AreaData.EMPTY || dirStep == AreaData.UNKNOW || dirStep == AreaData.OXYGEN) {
                    response.add(from.moveNew(direction));
                }
            }

            return response;
        }

    }

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2019/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        // init Intcode
        long[] opcodesArray = StringConverter.toLongArray(eventData);
        opcodes = new HashMap<>();
        comp = new IntcodeMapComputer();

        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        // compute full maze
        Map<SimplePos, AreaData> area = new HashMap<>();
        SimplePos currentPos = new SimplePos(0, 0);
        area.put(currentPos, AreaData.EMPTY);
        List<Direction> wayToNextUnknown = getWayToNextUnknown(area, currentPos);

        while (!wayToNextUnknown.isEmpty()) {
            currentPos = move(area, currentPos, wayToNextUnknown);
            wayToNextUnknown = getWayToNextUnknown(area, currentPos);
        }

        // print maze
        MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(area, v -> (v == null) ? ' ' : v.getValue()));

        SimplePos oxygenPos = area.entrySet().stream().filter(v -> v.getValue() == AreaData.OXYGEN).findFirst().orElseThrow().getKey();

        // do puzzle 1
        Dijkstra dijkstra = new Dijkstra();
        SimpleMaze maze = new SimpleMaze(area);
        stepsToOxygen = dijkstra.getSteps(//
                maze, //
                new SimplePos(0, 0), //
                oxygenPos //
        );

        // do puzzle 2
        dijkstra = new Dijkstra();
        fullSpreadTime = dijkstra.getFullSpreadTime(//
                maze, //
                oxygenPos //
        );

        logger.log(Level.INFO, () -> "stepsToOxygen : " + getStepsToOxygen());
        logger.log(Level.INFO, () -> "fullSpreadTime : " + getFullSpreadTime());

        return true;
    }

    private SimplePos move(Map<SimplePos, AreaData> areaOrigin, SimplePos start, List<Direction> way) {

        SimplePos lastPosition = start;
        SimplePos nextPosition = lastPosition;

        int i = 0;
        int lastOutput = -1;
        while (i < way.size()) {
            Direction d = way.get(i);
            i++;
            comp.setInput(directionToInput(d));
            lastPosition = nextPosition;
            nextPosition = lastPosition.moveNew(d);

            while (!comp.isEnd(opcodes, pos)) {
                pos = comp.runOpcodes(opcodes, pos);
                if (comp.isOutput()) {
                    lastOutput = comp.removeOutput().intValue();
                    break;
                }
            }
        }

        switch (lastOutput) {
        case 0:
            areaOrigin.put(nextPosition, AreaData.WALL);
            return lastPosition;
        case 1:
            areaOrigin.put(nextPosition, AreaData.EMPTY);
            break;
        case 2:
            areaOrigin.put(nextPosition, AreaData.OXYGEN);
            break;
        default:
            break;
        }

        return nextPosition;
    }

    private Long directionToInput(Direction d) {
        switch (d) {
        case N:
            return 1L;
        case S:
            return 2L;
        case W:
            return 3L;
        case E:
            return 4L;
        default:
            break;
        }
        throw new IllegalArgumentException("Wrong Direction!");
    }

    private List<Direction> getWayToNextUnknown(Map<SimplePos, AreaData> areaOrigin, SimplePos start) {
        Map<SimplePos, AreaData> area = new HashMap<>(areaOrigin);

        LinkedList<List<Direction>> toCheck = new LinkedList<>();

        area.put(start, AreaData.VISITED);

        for (Direction nb : getNextSteps(area, start)) {
            List<Direction> newWay = new LinkedList<>();
            newWay.add(nb);
            toCheck.add(newWay);
        }

        while (!toCheck.isEmpty()) {
            SimplePos to = start;

            List<Direction> way = toCheck.poll();

            for (Direction d : way) {
                area.put(to, AreaData.VISITED);
                to = to.moveNew(d);
            }

            if (area.getOrDefault(to, AreaData.UNKNOW) == AreaData.UNKNOW) {
                return way;
            }

            for (Direction nb : getNextSteps(area, to)) {
                List<Direction> newWay = new LinkedList<>(way);
                newWay.add(nb);
                toCheck.add(newWay);
            }
        }

        return Collections.emptyList();
    }

    private List<Direction> getNextSteps(Map<SimplePos, AreaData> area, SimplePos from) {
        List<Direction> response = new LinkedList<>();

        for (Direction direction : Direction.values()) {
            var dirStep = area.getOrDefault(from.moveNew(direction), AreaData.UNKNOW);
            if (dirStep == AreaData.EMPTY || dirStep == AreaData.UNKNOW || dirStep == AreaData.OXYGEN) {
                response.add(direction);
            }
        }

        return response;
    }

}
