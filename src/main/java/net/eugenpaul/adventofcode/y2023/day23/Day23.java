package net.eugenpaul.adventofcode.y2023.day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day23 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @AllArgsConstructor
    private class Way2 {
        SimplePos pos;
        int cost;
    }

    private class Way {
        SimplePos pos;
        Set<SimplePos> his = new HashSet<>();

        Way(SimplePos pos) {
            this.pos = pos;
        }

        Way(SimplePos pos, Set<SimplePos> his) {
            this.pos = pos;
            this.his = new HashSet<>(his);
        }
    }

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2023/day23/puzzle1.txt");
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

        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        var m = StringConverter.toMap(eventData);

        var start = new SimplePos(1, 0);
        var end = new SimplePos(maxX - 2, maxY - 1);
        var startPoint = new Way(start);
        startPoint.his.add(start);

        List<Way> toCheck = new LinkedList<>();
        toCheck.add(startPoint);

        while (!toCheck.isEmpty()) {
            var cur = toCheck.removeFirst();

            if (cur.pos.equals(end)) {
                response = cur.his.size() - 1;
                continue;
            }

            if (m.get(cur.pos) != '.') {
                var d = Direction.fromArrow(m.get(cur.pos));
                var nb = cur.pos.moveNew(d);
                if (nb.getX() < 0 || nb.getX() >= maxX || m.get(nb) == '#') {
                    continue;
                }
                if (!cur.his.contains(nb)) {
                    cur.his.add(nb);
                    var next = new Way(nb, cur.his);
                    toCheck.add(next);
                }
            } else {
                for (var nb : cur.pos.getNeighbors(false)) {
                    if (nb.getX() < 0 || nb.getX() >= maxX || nb.getY() < 0 || nb.getY() >= maxY || m.get(nb) == '#') {
                        continue;
                    }
                    if (!cur.his.contains(nb)) {
                        Set<SimplePos> nh = new HashSet<>(cur.his);
                        nh.add(nb);
                        toCheck.add(new Way(nb, nh));
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        var m = StringConverter.toMap(eventData);

        var start = new SimplePos(1, 0);
        var end = new SimplePos(maxX - 2, maxY - 1);
        Set<SimplePos> way = new HashSet<>();
        way.add(start);

        response = longestWay(start, m, end, maxX, maxY);

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private int longestWay(SimplePos start, Map<SimplePos, Character> m, SimplePos end, int maxX, int maxY) {

        m = m.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> (e.getValue() == '#') ? '#' : '.'));

        List<SimplePos> points = new ArrayList<>();
        points.add(start);
        points.add(end);

        for (int x = 1; x < maxX - 1; x++) {
            for (int y = 1; y < maxY - 1; y++) {
                SimplePos p = new SimplePos(x, y);
                if (m.get(p) == '.') {
                    int cnt = 0;
                    for (var nb : p.getNeighbors(false)) {
                        if (m.get(nb) == '.') {
                            cnt++;
                        }
                    }
                    if (cnt > 2) {
                        points.add(p);
                    }
                }
            }
        }

        Map<SimplePos, Map<SimplePos, Integer>> all = new HashMap<>();

        Set<SimplePos> pointsSet = new HashSet<>(points);

        for (int i = 0; i < points.size(); i++) {
            for (int k = i + 1; k < points.size(); k++) {
                var from = points.get(i);
                var to = points.get(k);
                var cost = findWay(from, to, pointsSet, m, maxX, maxY);
                if (cost > 0) {
                    all.computeIfAbsent(from, v -> new HashMap<>()).put(to, cost);
                    all.computeIfAbsent(to, v -> new HashMap<>()).put(from, cost);
                }
            }
        }

        Set<SimplePos> seen = new HashSet<>();
        seen.add(start);

        return longWay(start, end, seen, all);
    }

    private int longWay(SimplePos current, SimplePos end, Set<SimplePos> seen, Map<SimplePos, Map<SimplePos, Integer>> all) {
        if (current.equals(end)) {
            return 0;
        }

        int resp = -1;

        List<SimplePos> nextPoints = new LinkedList<>(all.get(current).keySet());

        for (var next : nextPoints) {
            if (seen.contains(next)) {
                continue;
            }
            seen.add(next);
            int cost = all.get(current).get(next);
            

            int restCost = longWay(next, end, seen, all);
            if(restCost >= 0){
                resp = Math.max(resp, restCost + cost);
            }
            seen.remove(next);
        }

        return resp;
    }

    private int findWay(SimplePos start, SimplePos end, Set<SimplePos> points, Map<SimplePos, Character> m, int maxX, int maxY) {
        var seen = new HashSet<SimplePos>();
        var toCheck = new LinkedList<Way2>();
        toCheck.add(new Way2(start, 0));
        while (!toCheck.isEmpty()) {
            var cur = toCheck.removeFirst();
            if (seen.contains(cur.pos)) {
                continue;
            }
            seen.add(cur.pos);
            if (cur.pos.equals(end)) {
                return cur.cost;
            }
            if (!start.equals(cur.pos) && points.contains(cur.pos)) {
                continue;
            }

            for (var nb : cur.pos.getNeighbors(false)) {
                if (nb.getX() < 0 || nb.getX() >= maxX || nb.getY() < 0 || nb.getY() >= maxY || m.get(nb) != '.') {
                    continue;
                }
                toCheck.add(new Way2(nb, cur.cost + 1));
            }
        }
        return -1;
    }

}
