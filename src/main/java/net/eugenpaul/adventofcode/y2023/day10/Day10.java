package net.eugenpaul.adventofcode.y2023.day10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day10 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @RequiredArgsConstructor
    @Getter
    private enum Tiles {
        VR('|', true, true, false, false), //
        HR('-', false, false, true, true), //
        NE('L', true, false, false, true), //
        NW('J', true, false, true, false), //
        SW('7', false, true, true, false), //
        SE('F', false, true, false, true), //
        GROUND('.', false, false, false, false), //
        START('S', true, true, true, true),//
        ;

        private final char value;
        private final boolean up;
        private final boolean down;
        private final boolean left;
        private final boolean right;

        private static final Map<Character, Tiles> m = Stream.of(Tiles.values()).collect(Collectors.toMap(d -> d.value, d -> d));

        public static Tiles fromChar(Character c) {
            return m.get(c);
        }
    }

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2023/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Tiles> m = StringConverter.toMap(eventData).entrySet().stream()//
                .collect(Collectors.toMap(Entry::getKey, e -> Tiles.fromChar(e.getValue())));

        SimplePos start = m.entrySet().stream().filter(e -> e.getValue() == Tiles.START).findFirst().orElseThrow().getKey();

        Direction first = null;
        Direction second = null;
        for (Direction d : Direction.values()) {
            if (isConnected(start, d, m)) {
                if (first == null) {
                    first = d;
                } else {
                    second = d;
                }
            }
        }

        if (first == null || second == null) {
            throw new IllegalArgumentException("first or second is null");
        }

        SimplePos nextFirst = start.moveNew(first);
        SimplePos nextSecond = start.moveNew(second);
        response = 1L;
        while (!nextFirst.equals(nextSecond)) {
            response++;
            for (Direction d : Direction.values()) {
                if (first.reverse() != d && isConnected(nextFirst, d, m)) {
                    nextFirst.move(d);
                    first = d;
                    break;
                }
            }
            for (Direction d : Direction.values()) {
                if (second.reverse() != d && isConnected(nextSecond, d, m)) {
                    nextSecond.move(d);
                    second = d;
                    break;
                }
            }
        }

        logger.info("Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        Map<SimplePos, Tiles> m = StringConverter.toMap(eventData).entrySet().stream()//
                .collect(Collectors.toMap(Entry::getKey, e -> Tiles.fromChar(e.getValue())));

        SimplePos start = m.entrySet().stream().filter(e -> e.getValue() == Tiles.START).findFirst().orElseThrow().getKey();

        Direction first = null;
        for (Direction d : Direction.values()) {
            if (isConnected(start, d, m)) {
                first = d;
                break;
            }
        }

        if (first == null) {
            throw new IllegalArgumentException("first is null");
        }

        Set<SimplePos> seen = new HashSet<>();
        SimplePos nextFirst = start.moveNew(first);
        seen.add(start);
        while (!nextFirst.equals(start)) {
            seen.add(nextFirst.copy());
            for (Direction d : Direction.values()) {
                if (first.reverse() != d && isConnected(nextFirst, d, m)) {
                    nextFirst.move(d);
                    first = d;
                    break;
                }
            }
        }

        //Remove not connected pipes from loop
        for(var entry:m.entrySet()){
            if(!seen.contains(entry.getKey())){
                entry.setValue(Tiles.GROUND);
            }
        }

        Map<SimplePos, Tiles> tmp = expandH(m);
        Map<SimplePos, Tiles> exp = expandV(tmp);

        int maxX = exp.keySet().stream().mapToInt(d -> d.getX()).max().orElseThrow() + 1;
        int maxY = exp.keySet().stream().mapToInt(d -> d.getY()).max().orElseThrow() + 1;

        for (int x = -1; x <= maxX; x++) {
            exp.put(new SimplePos(x, -1), Tiles.GROUND);
            exp.put(new SimplePos(x, maxY), Tiles.GROUND);
        }
        for (int y = -1; y <= maxY; y++) {
            exp.put(new SimplePos(-1, y), Tiles.GROUND);
            exp.put(new SimplePos(maxX, y), Tiles.GROUND);
        }

        //Mark ground fields outside the loop
        seen.clear();
        SimplePos s = new SimplePos(-1, -1);
        LinkedList<SimplePos> next = new LinkedList<>();
        seen.add(s);
        next.add(s.moveNew(Direction.E));
        next.add(s.moveNew(Direction.S));
        while (!next.isEmpty()) {
            var pos = next.pollFirst();
            if (seen.contains(pos)) {
                continue;
            }
            seen.add(pos);
            for (var d : Direction.values()) {
                var toCheck = pos.moveNew(d);
                if (exp.getOrDefault(toCheck, Tiles.START) == Tiles.GROUND) {
                    next.add(toCheck);
                }
            }
        }
        
        //count gound fields inside the loop
        for (int x = 0; x <= maxX; x += 2) {
            for (int y = 0; y <= maxY; y += 2) {
                var check = new SimplePos(x, y);
                var or = new SimplePos(x / 2, y / 2);
                if (m.get(or) == Tiles.GROUND && !seen.contains(check)) {
                    response++;
                }
            }
        }

        logger.info("Solution 2 " + response);
        return response;
    }

    private boolean isConnected(SimplePos a, Direction d, Map<SimplePos, Tiles> m) {
        Tiles s = m.getOrDefault(a, Tiles.GROUND);
        Tiles e = m.getOrDefault(a.moveNew(d), Tiles.GROUND);

        return switch (d) {
        case N -> s.up && e.down;
        case S -> s.down && e.up;
        case E -> s.right && e.left;
        case W -> s.left && e.right;
        };
    }

    private Map<SimplePos, Tiles> expandH(Map<SimplePos, Tiles> m) {
        Map<SimplePos, Tiles> resp = new HashMap<>();
        int maxX = m.keySet().stream().mapToInt(d -> d.getX()).max().orElseThrow();
        int maxY = m.keySet().stream().mapToInt(d -> d.getY()).max().orElseThrow();

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                SimplePos l = new SimplePos(x, y);
                resp.put(new SimplePos(x * 2, y), m.get(l));
                resp.put(new SimplePos(x * 2 + 1, y), (isConnected(l, Direction.E, m) ? Tiles.HR : Tiles.GROUND));
            }
            SimplePos l = new SimplePos(maxX, y);
            resp.put(new SimplePos(maxX * 2, y), m.get(l));
        }

        return resp;
    }

    private Map<SimplePos, Tiles> expandV(Map<SimplePos, Tiles> m) {
        Map<SimplePos, Tiles> resp = new HashMap<>();
        int maxX = m.keySet().stream().mapToInt(d -> d.getX()).max().orElseThrow();
        int maxY = m.keySet().stream().mapToInt(d -> d.getY()).max().orElseThrow();

        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                SimplePos l = new SimplePos(x, y);
                resp.put(new SimplePos(x, y * 2), m.get(l));
                resp.put(new SimplePos(x, y * 2 + 1), (isConnected(l, Direction.S, m) ? Tiles.VR : Tiles.GROUND));
            }
            SimplePos l = new SimplePos(x, maxY);
            resp.put(new SimplePos(x, maxY * 2), m.get(l));
        }

        return resp;
    }

}
