package net.eugenpaul.adventofcode.y2023.day16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day16 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @AllArgsConstructor
    @Data
    private class Step {
        SimplePos pos;
        Direction dir;
    }

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2023/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        response = check(eventData, new SimplePos(0, 0), Direction.E);

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private int check(List<String> eventData, SimplePos startPos, Direction startDir) {
        final int maxX = eventData.get(0).length();
        final int maxY = eventData.size();

        var m = StringConverter.toMap(eventData);
        Set<Step> seen = new HashSet<>();

        List<Step> toCheck = new LinkedList<>();
        toCheck.add(new Step(startPos, startDir));
        while (!toCheck.isEmpty()) {
            var next = toCheck.removeFirst();
            if (next.pos.getX() < 0 || next.pos.getX() >= maxX || next.pos.getY() < 0 || next.pos.getY() >= maxY || seen.contains(next)) {
                continue;
            }
            seen.add(next);
            var c = m.get(next.pos);
            if (c == '.' //
                    || (c == '|' && (next.dir == Direction.N || next.dir == Direction.S)) //
                    || (c == '-' && (next.dir == Direction.W || next.dir == Direction.E)) //
            ) {
                toCheck.add(new Step(next.getPos().moveNew(next.dir), next.dir));
            } else if ((c == '-' && (next.dir == Direction.N || next.dir == Direction.S)) //
                    || (c == '|' && (next.dir == Direction.W || next.dir == Direction.E)) //
            ) {
                toCheck.add(new Step(next.getPos().moveNew(next.dir.turnLeft()), next.dir.turnLeft()));
                toCheck.add(new Step(next.getPos().moveNew(next.dir.turnRight()), next.dir.turnRight()));
            } else if (c == '\\' && next.dir == Direction.S) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.E), Direction.E));
            } else if (c == '\\' && next.dir == Direction.N) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.W), Direction.W));
            } else if (c == '\\' && next.dir == Direction.E) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.S), Direction.S));
            } else if (c == '\\' && next.dir == Direction.W) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.N), Direction.N));
            } else if (c == '/' && next.dir == Direction.S) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.W), Direction.W));
            } else if (c == '/' && next.dir == Direction.N) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.E), Direction.E));
            } else if (c == '/' && next.dir == Direction.E) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.N), Direction.N));
            } else if (c == '/' && next.dir == Direction.W) {
                toCheck.add(new Step(next.getPos().moveNew(Direction.S), Direction.S));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return seen.stream().map(v -> v.pos).collect(Collectors.toSet()).size();
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        final int maxX = eventData.get(0).length();
        final int maxY = eventData.size();

        for (int x = 0; x < maxX; x++) {
            response = Math.max(response, check(eventData, new SimplePos(x, 0), Direction.S));
            response = Math.max(response, check(eventData, new SimplePos(x, maxY - 1), Direction.N));
        }
        for (int y = 0; y < maxY; y++) {
            response = Math.max(response, check(eventData, new SimplePos(0, y), Direction.E));
            response = Math.max(response, check(eventData, new SimplePos(maxX - 1, y), Direction.W));
        }
        
        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
