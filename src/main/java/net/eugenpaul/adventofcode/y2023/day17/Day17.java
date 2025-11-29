package net.eugenpaul.adventofcode.y2023.day17;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day17 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @AllArgsConstructor
    @Data
    private class Position {
        int cost;
        SimplePos pos;
        Direction dir;
    }

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2023/day17/puzzle1.txt");
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

    private record Step(SimplePos pos, Direction dir, Long cost) {
    }

    private record PD(SimplePos pos, Direction dir) {
    }

    public long doPuzzle1(List<String> eventData) {
        long response = -1;

        int maxX = eventData.get(0).length() - 1;
        int maxY = eventData.size() - 1;
        var end = new SimplePos(maxX, maxY);

        PriorityQueue<Step> way = new PriorityQueue<>((a, b) -> Long.compare(a.cost, b.cost));
        way.add(new Step(new SimplePos(0, 0), Direction.E, 0L));
        way.add(new Step(new SimplePos(0, 0), Direction.S, 0L));

        Set<PD> seen = new HashSet<>();

        while (true) {
            var cur = way.poll();
            if (seen.contains(new PD(cur.pos, cur.dir))) {
                continue;
            }
            seen.add(new PD(cur.pos, cur.dir));

            if (cur.pos.equals(end)) {
                response = cur.cost;
                break;
            }
            var nxt = cur.pos;
            var c = cur.cost;

            // 1
            nxt = nxt.moveNew(cur.dir);
            if (!nxt.inRange(0, maxY, 0, maxY)) {
                continue;
            }
            c = doStep(eventData, way, seen, cur, nxt, c);

            // 2
            nxt = nxt.moveNew(cur.dir);
            if (!nxt.inRange(0, maxY, 0, maxY)) {
                continue;
            }
            c = doStep(eventData, way, seen, cur, nxt, c);

            // 3
            nxt = nxt.moveNew(cur.dir);
            if (!nxt.inRange(0, maxY, 0, maxY)) {
                continue;
            }
            c = doStep(eventData, way, seen, cur, nxt, c);
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = -1;

        int maxX = eventData.get(0).length() - 1;
        int maxY = eventData.size() - 1;
        var end = new SimplePos(maxX, maxY);

        PriorityQueue<Step> way = new PriorityQueue<>((a, b) -> Long.compare(a.cost, b.cost));
        way.add(new Step(new SimplePos(0, 0), Direction.E, 0L));
        way.add(new Step(new SimplePos(0, 0), Direction.S, 0L));

        Set<PD> seen = new HashSet<>();

        while (true) {
            var cur = way.poll();
            if (seen.contains(new PD(cur.pos, cur.dir))) {
                continue;
            }
            seen.add(new PD(cur.pos, cur.dir));

            if (cur.pos.equals(end)) {
                response = cur.cost;
                break;
            }
            var nxt = cur.pos;
            var c = cur.cost;

            for (int i = 0; i < 3; i++) {
                nxt = nxt.moveNew(cur.dir);
                if (!nxt.inRange(0, maxY, 0, maxY)) {
                    break;
                }
                c += eventData.get(nxt.getY()).charAt(nxt.getX()) - '0';
            }

            for (int i = 3; i < 10; i++) {
                nxt = nxt.moveNew(cur.dir);
                if (!nxt.inRange(0, maxY, 0, maxY)) {
                    break;
                }
                c = doStep(eventData, way, seen, cur, nxt, c);
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private Long doStep(List<String> eventData, PriorityQueue<Step> way, Set<PD> seen, Step cur, SimplePos nxt, Long c) {
        c += eventData.get(nxt.getY()).charAt(nxt.getX()) - '0';
        if (!seen.contains(new PD(nxt, cur.dir.turnLeft()))) {
            way.add(new Step(nxt, cur.dir.turnLeft(), c));
        }
        if (!seen.contains(new PD(nxt, cur.dir.turnRight()))) {
            way.add(new Step(nxt, cur.dir.turnRight(), c));
        }
        return c;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        final int maxX = eventData.get(0).length();
        final int maxY = eventData.size();

        var m = StringConverter.toMap(eventData).entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> (int) e.getValue() - '0'));
        SimplePos start = new SimplePos(0, 0);
        Map<SimplePos, Set<Direction>> seen = new HashMap<>();

        Comparator<Position> comparator = (a, b) -> Integer.compare(a.cost, b.cost);

        PriorityQueue<Position> toCheck = new PriorityQueue<>(comparator);
        toCheck.add(new Position(0, start, Direction.E));
        toCheck.add(new Position(0, start, Direction.S));

        while (!toCheck.isEmpty()) {
            var next = toCheck.poll();
            if (seen.getOrDefault(next.pos, Collections.emptySet()).contains(next.dir)) {
                continue;
            }

            seen.computeIfAbsent(next.pos, k -> EnumSet.noneOf(Direction.class)).add(next.dir);

            if (next.pos.equals(new SimplePos(maxX - 1, maxY - 1))) {
                response = next.cost + m.get(next.pos) - m.get(new SimplePos(0, 0));
                break;
            }
            // 1 Step
            var nextPos1 = next.pos.moveNew(next.dir);
            if (nextPos1.getX() >= 0 && nextPos1.getX() < maxX && nextPos1.getY() >= 0 && nextPos1.getY() < maxY) {
                toCheck.add(new Position(next.cost + m.get(next.pos), nextPos1, next.dir.turnLeft()));
                toCheck.add(new Position(next.cost + m.get(next.pos), nextPos1, next.dir.turnRight()));
            }

            // 2 Step
            var nextPos2 = next.pos.moveNew(next.dir).move(next.dir);
            if (nextPos2.getX() >= 0 && nextPos2.getX() < maxX && nextPos2.getY() >= 0 && nextPos2.getY() < maxY) {
                toCheck.add(new Position(next.cost + m.get(next.pos) + m.get(nextPos1), nextPos2, next.dir.turnLeft()));
                toCheck.add(new Position(next.cost + m.get(next.pos) + m.get(nextPos1), nextPos2, next.dir.turnRight()));
            }

            // 3 Step
            var nextPos3 = next.pos.moveNew(next.dir).move(next.dir).move(next.dir);
            if (nextPos3.getX() >= 0 && nextPos3.getX() < maxX && nextPos3.getY() >= 0 && nextPos3.getY() < maxY) {
                toCheck.add(new Position(next.cost + m.get(next.pos) + m.get(nextPos1) + m.get(nextPos2), nextPos3, next.dir.turnLeft()));
                toCheck.add(new Position(next.cost + m.get(next.pos) + m.get(nextPos1) + m.get(nextPos2), nextPos3, next.dir.turnRight()));
            }

        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2_a(List<String> eventData) {
        long response = 0;

        final int maxX = eventData.get(0).length();
        final int maxY = eventData.size();

        var m = StringConverter.toMap(eventData).entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> (int) e.getValue() - '0'));
        SimplePos start = new SimplePos(0, 0);
        Map<SimplePos, Set<Direction>> seen = new HashMap<>();

        Comparator<Position> comparator = (a, b) -> Integer.compare(a.cost, b.cost);

        PriorityQueue<Position> toCheck = new PriorityQueue<>(comparator);
        toCheck.add(new Position(0, start, Direction.E));
        toCheck.add(new Position(0, start, Direction.S));

        while (!toCheck.isEmpty()) {
            var next = toCheck.poll();
            if (seen.getOrDefault(next.pos, Collections.emptySet()).contains(next.dir)) {
                continue;
            }

            seen.computeIfAbsent(next.pos, k -> EnumSet.noneOf(Direction.class)).add(next.dir);

            if (next.pos.equals(new SimplePos(maxX - 1, maxY - 1))) {
                response = next.cost + m.get(next.pos) - m.get(new SimplePos(0, 0));
                break;
            }

            int cost = next.cost;
            var pos = next.pos;
            for (int i = 1; i <= 10; i++) {
                cost += m.get(pos);
                pos = pos.moveNew(next.dir);
                if (pos.getX() >= 0 && pos.getX() < maxX && pos.getY() >= 0 && pos.getY() < maxY) {
                    if (i >= 4) {
                        toCheck.add(new Position(cost, pos, next.dir.turnLeft()));
                        toCheck.add(new Position(cost, pos, next.dir.turnRight()));
                    }
                } else {
                    break;
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
