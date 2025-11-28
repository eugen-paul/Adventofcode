package net.eugenpaul.adventofcode.y2023.day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day15 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @AllArgsConstructor
    @ToString
    private class Lens {
        private String name;
        private int power;
    }

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2023/day15/puzzle1.txt");
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

        response = Arrays.stream(eventData.get(0).split(",")) //
                .mapToLong(s -> s.chars().reduce(0, (a, b) -> ((a + b) * 17) % 256)) //
                .sum();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        TreeMap<Integer, LinkedHashMap<String, Integer>> boxes = new TreeMap<>();

        Arrays.stream(eventData.get(0).split(",")) //
                .forEach(v -> {
                    boolean add = v.contains("=");
                    String[] d = v.split("[=\\-]");
                    int h = d[0].chars().reduce(0, (a, b) -> ((a + b) * 17) % 256);
                    if (add) {
                        boxes.computeIfAbsent(h, k -> new LinkedHashMap<>()) //
                                .put(d[0], Integer.parseInt(d[1]));
                    } else {
                        boxes.computeIfAbsent(h, k -> new LinkedHashMap<>()) //
                                .remove(d[0]);
                    }
                });
        response = boxes.entrySet().stream().mapToLong(v -> {
            long r = 0;
            long cnt = 1;
            while (!v.getValue().isEmpty()) {
                var e = v.getValue().pollFirstEntry();
                r += (1 + v.getKey()) * (cnt) * e.getValue();
                cnt++;
            }
            return r;
        }).sum();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle1a(List<String> eventData) {
        long response = 0;

        response = Stream.of(eventData.get(0).split(",")).mapToLong(this::hash).sum();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long hash(String data) {
        long value = 0;

        for (char c : data.toCharArray()) {
            value = ((value + c) * 17) % 256;
        }

        return value;
    }

    public long doPuzzle2a(List<String> eventData) {
        long response = 0;

        Map<Integer, List<Lens>> m = new HashMap<>();

        Stream.of(eventData.get(0).split(",")).forEach(v -> action(m, v));

        for (var entry : m.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                response += ((i + 1) * entry.getValue().get(i).power) * (entry.getKey() + 1);
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private void action(Map<Integer, List<Lens>> m, String a) {
        if (a.contains("-")) {
            String label = a.substring(0, a.length() - 1);
            removeAction(m, (int) hash(label), label);
        } else {
            var splits = a.split("=");
            String label = splits[0];
            addAction(m, (int) hash(label), new Lens(label, Integer.valueOf(splits[1])));
        }
    }

    private void addAction(Map<Integer, List<Lens>> m, int box, Lens lens) {
        List<Lens> listLens = m.computeIfAbsent(box, k -> new LinkedList<>());
        boolean found = false;
        for (var l : listLens) {
            if (l.name.equals(lens.name)) {
                found = true;
                l.power = lens.power;
                break;
            }
        }
        if (!found) {
            listLens.add(lens);
        }
    }

    private void removeAction(Map<Integer, List<Lens>> m, int box, String lens) {
        List<Lens> listLens = m.computeIfAbsent(box, k -> new LinkedList<>());
        listLens.removeIf(v -> v.name.equals(lens));
    }

}
