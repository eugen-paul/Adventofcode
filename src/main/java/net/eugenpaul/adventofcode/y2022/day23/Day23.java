package net.eugenpaul.adventofcode.y2022.day23;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day23 extends SolutionTemplate {

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2022/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        var area = readMap(eventData);
        var moveDirections = new LinkedList<>(List.of(Direction.N, Direction.S, Direction.W, Direction.E));

        for (int i = 0; i < 10; i++) {
            doRound(area, moveDirections);
        }

        int xMin = area.stream().mapToInt(SimplePos::getX).min().orElseThrow();
        int xMax = area.stream().mapToInt(SimplePos::getX).max().orElseThrow();
        int yMin = area.stream().mapToInt(SimplePos::getY).min().orElseThrow();
        int yMax = area.stream().mapToInt(SimplePos::getY).max().orElseThrow();

        return (xMax - xMin + 1) * (yMax - yMin + 1) - (long) area.size();
    }

    private boolean doRound(Set<SimplePos> area, LinkedList<Direction> moveDirections) {
        // All elves that want to move.
        Set<SimplePos> wantToMove = area.stream() //
                .filter(v -> v.getNeighbors(true).stream().anyMatch(area::contains)) //
                .collect(Collectors.toSet());

        // All elves that can move.
        Set<SimplePos> cannMove = wantToMove.stream() //
                .filter(v -> getMoving(area, v, moveDirections) != null) //
                .collect(Collectors.toSet());

        // Moving target of that elfs.
        Map<SimplePos, Integer> moveTarget = cannMove.stream()//
                .map(v -> v.moveNew(getMoving(area, v, moveDirections))) //
                .collect(Collectors.toMap(k -> k, v -> 1, (v1, v2) -> v1 + v2));

        // All elves that will move.
        Set<SimplePos> willMove = cannMove.stream()//
                .filter(v -> moveTarget.get(v.moveNew(getMoving(area, v, moveDirections))) == 1) //
                .collect(Collectors.toSet());

        // Move elf if the target ist unique
        Set<SimplePos> afterMove = willMove.stream()//
                .map(v -> v.moveNew(getMoving(area, v, moveDirections))) //
                .collect(Collectors.toSet());

        area.removeIf(willMove::contains);
        area.addAll(afterMove);

        moveDirections.addLast(moveDirections.pollFirst());

        return !willMove.isEmpty();
    }

    private Direction getMoving(Set<SimplePos> area, SimplePos moveElf, List<Direction> moveDirections) {
        for (var d : moveDirections) {
            switch (d) {
            case N, S:
                if (!area.contains(moveElf.moveNew(d)) //
                        && !area.contains(moveElf.moveNew(d).move(Direction.E)) //
                        && !area.contains(moveElf.moveNew(d).move(Direction.W)) //
                ) {
                    return d;
                }
                break;
            case W, E:
                if (!area.contains(moveElf.moveNew(d)) //
                        && !area.contains(moveElf.moveNew(d).move(Direction.N)) //
                        && !area.contains(moveElf.moveNew(d).move(Direction.S)) //
                ) {
                    return d;
                }
                break;
            default:
                throw new IllegalArgumentException();
            }
        }
        return null;
    }

    private long doPuzzle2(List<String> eventData) {
        var area = readMap(eventData);
        var moveDirections = new LinkedList<>(List.of(Direction.N, Direction.S, Direction.W, Direction.E));
        int response = 1;
        while (doRound(area, moveDirections)) {
            response++;
        }
        return response;
    }

    private Set<SimplePos> readMap(List<String> eventData) {
        return StringConverter.toSet(eventData, '#');
    }
}
