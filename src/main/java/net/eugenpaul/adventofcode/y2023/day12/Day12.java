package net.eugenpaul.adventofcode.y2023.day12;

import java.util.Collections;
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

        for (var data : eventData) {
            var in = data.split(" ")[0];
            var cnt = StringConverter.toIntegerArrayList(data.split(" ")[1]);
            response += cnt(in, cnt, 0, new HashMap<>());
        }

        logger.info("Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        for (var data : eventData) {
            var in = data.split(" ")[0];
            in = String.join("?", Collections.nCopies(5, in));
            var cnt = StringConverter.toIntegerArrayList(data.split(" ")[1]);
            cnt = Collections.nCopies(5, cnt).stream().flatMap(v -> v.stream()).toList();
            response += cnt(in, cnt, 0, new HashMap<>());
        }

        logger.info("Solution 2 " + response);
        return response;
    }

    private long cnt(String data, List<Integer> numbers, int posNumbers, Map<Integer, Map<Integer, Long>> cache) {
        if (cache.computeIfAbsent(data.length(), k -> new HashMap<>()) //
                .containsKey(posNumbers)) //
        {
            return cache.computeIfAbsent(data.length(), k -> new HashMap<>())//
                    .get(posNumbers);
        }

        if (posNumbers == numbers.size()) {
            if (data.isEmpty() || data.indexOf('#') == -1) {
                cache.get(data.length()).put(posNumbers, 1L);
                return 1;
            }
            cache.get(data.length()).put(posNumbers, 0L);
            return 0;
        }

        if (data.isEmpty()) {
            cache.get(data.length()).put(posNumbers, 0L);
            return 0;
        }

        long r = 0;
        if (data.charAt(0) != '#') {
            r = cnt(data.substring(1), numbers, posNumbers, cache);
        }

        int curLen = numbers.get(posNumbers);
        if (data.charAt(0) != '.' && isOk(data, curLen)) {
            if (curLen == data.length()) {
                r += cnt("", numbers, posNumbers + 1, cache);
            } else {
                r += cnt(data.substring(curLen + 1), numbers, posNumbers + 1, cache);
            }
        }

        cache.get(data.length()).put(posNumbers, r);

        return r;
    }

    private boolean isOk(String in, int len) {
        if (in.length() < len) {
            return false;
        }
        String sub = in.substring(0, len);
        return sub.replace(".", "").length() == len && (len == in.length() || in.charAt(len) != '#');
    }

    public long doPuzzle1_a(List<String> eventData) {
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

    public long doPuzzle2_a(List<String> eventData) {
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
