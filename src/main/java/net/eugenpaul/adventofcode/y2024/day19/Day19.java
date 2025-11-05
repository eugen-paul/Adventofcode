package net.eugenpaul.adventofcode.y2024.day19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    private record CacheData() {
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2024/day19/puzzle1.txt");
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

        List<String> towels = Arrays.stream(eventData.get(0).split(", ")).toList();

        for (int i = 2; i < eventData.size(); i++) {
            if (check(towels, 0, eventData.get(i), new HashSet<>())) {
                response++;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private boolean check(List<String> towels, int pos, String target, Set<String> cache) {
        if (target.isEmpty()) {
            return true;
        }

        if (cache.contains(target)) {
            return false;
        }

        for (int i = pos; i < towels.size(); i++) {
            String t = towels.get(i);
            if (target.startsWith(t) && check(towels, 0, target.substring(t.length()), cache)) {
                return true;
            }
        }

        cache.add(target);
        return false;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<String> towels = Arrays.stream(eventData.get(0).split(", ")).toList();

        for (int i = 2; i < eventData.size(); i++) {
            response += check2(towels, eventData.get(i), new HashMap<>());
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long check2(List<String> towels, String target, Map<String, Long> cache) {
        if (target.isEmpty()) {
            return 1;
        }

        if (cache.containsKey(target)) {
            return cache.get(target);
        }

        long r = 0;

        for (String t : towels) {
            if (target.startsWith(t)) {
                r += check2(towels, target.substring(t.length()), cache);
            }
        }

        cache.put(target, r);
        return r;
    }

}
