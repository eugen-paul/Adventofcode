package net.eugenpaul.adventofcode.y2023.day12;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day12 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private Map<Integer, Long> cache = new HashMap<>();

    private static class Line {
        String spring;
        List<Integer> cond;

        public static Line fromString(String data) {
            var splits = data.split(" ");
            var response = new Line();
            response.spring = splits[0];
            response.cond = StringConverter.toIntegerLinkedList(splits[1]);
            return response;
        }
    }

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2023/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<Line> lines = eventData.stream().map(Line::fromString).toList();
        long delta;
        for (var line : lines) {
            cache.clear();
            delta = countCond(line.spring, 0, line.cond);
            response += delta;
        }

        logger.info("Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        List<Line> lines = eventData.stream().map(Line::fromString).toList();
        long delta;
        for (var line : lines) {
            String sp = line.spring + '?' + line.spring + '?' + line.spring + '?' + line.spring + '?' + line.spring;
            List<Integer> cd = new LinkedList<>();
            cd.addAll(line.cond);
            cd.addAll(line.cond);
            cd.addAll(line.cond);
            cd.addAll(line.cond);
            cd.addAll(line.cond);

            cache.clear();
            delta = countCond(sp, 0, cd);
            response += delta;
        }

        logger.info("Solution 2 " + response);
        return response;
    }

    public long countCond(String line, int start, List<Integer> cond) {
        Integer h = start * 1_000 + cond.size();
        if (cache.containsKey(h)) {
            return cache.get(h);
        }

        if (line.length() == start && cond.isEmpty()) {
            return 1;
        }

        if (line.length() + 1 == start && cond.isEmpty()) {
            return 1;
        }

        if (line.length() == start && !cond.isEmpty()) {
            return 0;
        }

        if (line.length() < start) {
            return 0;
        }

        if (!cond.isEmpty() && line.length() - start < cond.size() * 2 - 1) {
            return 0;
        }

        int nextH = line.indexOf('#', start);
        if (cond.isEmpty() && nextH == -1) {
            return 1;
        }
        if (cond.isEmpty() && nextH != -1) {
            return 0;
        }

        long resp = 0;
        if (isOk(line, start, cond.get(0))) {
            Integer st = cond.removeFirst();
            resp = countCond(line, start + st + 1, cond);
            cond.addFirst(st);
        }
        if (line.charAt(start) != '#') {
            resp += countCond(line, start + 1, cond);
        }
        cache.put(h, resp);
        return resp;
    }

    private boolean isOk(String line, int start, int cnt) {
        if (line.length() - start < cnt) {
            return false;
        }
        int posP = line.indexOf('.', start);
        if (posP != -1 && posP < start + cnt) {
            return false;
        }
        if (start + cnt < line.length() && line.charAt(start + cnt) == '#') {
            return false;
        }
        return true;
    }

}
