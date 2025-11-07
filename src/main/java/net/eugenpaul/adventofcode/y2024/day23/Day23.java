package net.eugenpaul.adventofcode.y2024.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day23 extends SolutionTemplate {

    private record ThreeSet(String a, String b, String c) {
        ThreeSet(String a, String b, String c) {
            var list = Arrays.stream(new String[] { a, b, c }).sorted().toList();
            this.a = list.get(0);
            this.b = list.get(1);
            this.c = list.get(2);
        }
    }

    @Getter
    private long totalScore;
    @Getter
    private String totalScore2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2024/day23/puzzle1.txt");
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

        Map<String, Set<String>> connections = new HashMap<>();
        for (String c : eventData) {
            var splits = c.split("-");
            connections.computeIfAbsent(splits[0], k -> new HashSet<>())//
                    .add(splits[1]);
            connections.computeIfAbsent(splits[1], k -> new HashSet<>())//
                    .add(splits[0]);
        }

        Set<ThreeSet> respSet = new HashSet<>();

        for (var e : connections.entrySet()) {
            if (!e.getKey().startsWith("t")) {
                continue;
            }
            Set<String> values = e.getValue();
            List<String> valuesList = values.stream().toList();
            for (int b = 0; b < valuesList.size(); b++) {
                for (int c = b + 1; c < valuesList.size(); c++) {
                    var bName = valuesList.get(b);
                    var cName = valuesList.get(c);
                    if (connections.get(bName).contains(cName)) {
                        respSet.add(new ThreeSet(e.getKey(), bName, cName));
                    }
                }
            }
        }

        response = respSet.size();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public String doPuzzle2(List<String> eventData) {
        String response;

        Map<String, Set<String>> connections = new HashMap<>();
        for (String c : eventData) {
            var splits = c.split("-");
            connections.computeIfAbsent(splits[0], k -> new HashSet<>())//
                    .add(splits[1]);
            connections.computeIfAbsent(splits[1], k -> new HashSet<>())//
                    .add(splits[0]);
        }

        Map<String, Boolean> cache = new HashMap<>();

        List<String> rList = Collections.emptyList();

        for (var e : connections.entrySet()) {
            List<String> nb = e.getValue().stream().toList();
            int m = 1 << nb.size();
            for (int i = 1; i < m; i++) {
                List<String> tmp = new ArrayList<>();
                for (int k = 0; k < nb.size(); k++) {
                    if ((i & (1 << k)) != 0) {
                        tmp.add(nb.get(k));
                    }
                }
                tmp.sort(Comparable::compareTo);
                if (fullConnected(tmp, connections, cache)) {
                    if (rList.size() < tmp.size() + 1) {
                        tmp.add(e.getKey());
                        rList = tmp;
                    }
                }
            }
        }

        rList.sort(Comparable::compareTo);

        response = String.join(",", rList);

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private boolean fullConnected(List<String> nb, Map<String, Set<String>> connections, Map<String, Boolean> cache) {
        if (nb.size() == 1) {
            return true;
        }

        String c = String.join("", nb);
        if (cache.containsKey(c)) {
            return cache.get(c);
        }

        String first = nb.get(0);
        Set<String> firstCon = connections.get(first);
        for (int i = 1; i < nb.size(); i++) {
            if (!firstCon.contains(nb.get(i))) {
                cache.put(c, false);
                return false;
            }
        }

        List<String> nbRest = nb.subList(1, nb.size());
        boolean allCon = fullConnected(nbRest, connections, cache);
        cache.put(c, allCon);
        return allCon;
    }

}
