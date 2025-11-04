package net.eugenpaul.adventofcode.y2024.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day12 extends SolutionTemplate {

    private record Per(SimplePos in, SimplePos out) {
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2024/day12/puzzle1.txt");
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
        long i = 0;

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        Set<SimplePos> globalVisited = new HashSet<>();

        Map<String, Long> area = new HashMap<>();
        Map<String, Long> perimeter = new HashMap<>();

        for (var s : m.keySet()) {
            if (globalVisited.contains(s)) {
                continue;
            }
            globalVisited.add(s);

            Character curChar = m.get(s);
            String curName = m.get(s) + "_" + i;
            i++;

            Set<SimplePos> visited = new HashSet<>();
            visited.add(s);
            LinkedList<SimplePos> toCheck = new LinkedList<>();
            toCheck.add(s);
            while (!toCheck.isEmpty()) {
                SimplePos cur = toCheck.pollFirst();
                if (!m.getOrDefault(cur, '-').equals(curChar)) {
                    continue;
                }
                globalVisited.add(cur);
                area.compute(curName, (k, v) -> v == null ? 1 : v + 1);
                for (SimplePos nb : cur.getNeighbors(false)) {
                    if (!m.getOrDefault(nb, '-').equals(curChar)) {
                        perimeter.compute(curName, (k, v) -> v == null ? 1 : v + 1);
                    }
                    if (visited.contains(nb)) {
                        continue;
                    }
                    visited.add(nb);
                    toCheck.add(nb);
                }
            }
        }

        for (String g : area.keySet()) {
            response += area.get(g) * perimeter.get(g);
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        long i = 0;

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        Set<SimplePos> globalVisited = new HashSet<>();

        Map<String, Long> area = new HashMap<>();
        Map<String, List<Per>> perimeter = new HashMap<>();

        for (var s : m.keySet()) {
            if (globalVisited.contains(s)) {
                continue;
            }
            globalVisited.add(s);

            Character curChar = m.get(s);
            String curName = m.get(s) + "_" + i;
            i++;

            Set<SimplePos> visited = new HashSet<>();
            visited.add(s);
            LinkedList<SimplePos> toCheck = new LinkedList<>();
            toCheck.add(s);
            while (!toCheck.isEmpty()) {
                SimplePos cur = toCheck.pollFirst();
                if (!m.getOrDefault(cur, '-').equals(curChar)) {
                    continue;
                }
                globalVisited.add(cur);
                area.compute(curName, (k, v) -> v == null ? 1 : v + 1);
                for (SimplePos nb : cur.getNeighbors(false)) {
                    if (!m.getOrDefault(nb, '-').equals(curChar)) {
                        perimeter.compute(curName, (k, v) -> {
                            if (v == null) {
                                v = new ArrayList<>();
                            }
                            v.add(new Per(cur, nb));
                            return v;
                        });
                    }
                    if (visited.contains(nb)) {
                        continue;
                    }
                    visited.add(nb);
                    toCheck.add(nb);
                }
            }
        }

        Map<String, Long> respPerimeter = new HashMap<>();

        for (var e : perimeter.entrySet()) {
            Set<Per> allPers = new HashSet<>(e.getValue());
            for (int a = 0; a < e.getValue().size(); a++) {
                Per p = e.getValue().get(a);
                if (!allPers.contains(p)) {
                    continue;
                }
                allPers.remove(p);
                respPerimeter.compute(e.getKey(), (k, v) -> v == null ? 1 : v + 1);
                SimplePos delta = p.out.subNew(p.in);
                Direction dir;
                if (delta.getX() == 0) {
                    dir = Direction.W;
                } else {
                    dir = Direction.S;
                }
                SimplePos inT = p.in.moveNew(dir);
                SimplePos outT = p.out.moveNew(dir);
                Per t = new Per(inT, outT);
                while(allPers.contains(t)){
                    allPers.remove(t);
                    inT = t.in.moveNew(dir);
                    outT = t.out.moveNew(dir);
                    t = new Per(inT, outT);
                }

                dir = dir.reverse();

                inT = p.in.moveNew(dir);
                outT = p.out.moveNew(dir);
                t = new Per(inT, outT);
                while(allPers.contains(t)){
                    allPers.remove(t);
                    inT = t.in.moveNew(dir);
                    outT = t.out.moveNew(dir);
                    t = new Per(inT, outT);
                }
            }
        }

        for (String g : area.keySet()) {
            response += area.get(g) * respPerimeter.get(g);
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
