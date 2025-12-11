package net.eugenpaul.adventofcode.y2025.day11;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day11 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2025/day11/puzzle1.txt");
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

        Map<String, List<String>> m = new HashMap<>();
        for (var d : eventData) {
            String from = d.substring(0, d.indexOf(":"));
            String[] splits = d.split(" ");
            List<String> tos = new ArrayList<>();
            for (int i = 1; i < splits.length; i++) {
                tos.add(splits[i]);
            }
            m.put(from, tos);
        }

        response = path(m, "you", new HashMap<>());

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long path(Map<String, List<String>> m, String from, HashMap<String, Long> cache) {
        if (cache.containsKey(from)) {
            return cache.get(from);
        }
        if (from.equals("out")) {
            return 1;
        }

        long r = 0;
        for (var to : m.get(from)) {
            r += path(m, to, cache);
        }

        cache.put(from, r);

        return r;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Map<String, List<String>> m = new HashMap<>();
        for (var d : eventData) {
            String from = d.substring(0, d.indexOf(":"));
            String[] splits = d.split(" ");
            List<String> tos = new ArrayList<>();
            for (int i = 1; i < splits.length; i++) {
                tos.add(splits[i]);
            }
            m.put(from, tos);
        }

        response = path2(m, "svr", false, false, new HashMap<>());

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long path2(Map<String, List<String>> m, String from, boolean dac, boolean fft, HashMap<String, Long> cache) {
        String key = from + dac + fft;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (from.equals("out")) {
            if (dac && fft) {
                return 1;
            }
            return 0;
        }

        long r = 0;
        for (var to : m.get(from)) {
            r += path2(m, to, dac || to.equals("dac"), fft || to.equals("fft"), cache);
        }

        cache.put(key, r);

        return r;
    }

}
