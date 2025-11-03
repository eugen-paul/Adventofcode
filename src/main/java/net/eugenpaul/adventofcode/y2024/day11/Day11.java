package net.eugenpaul.adventofcode.y2024.day11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day11 extends SolutionTemplate {

    private record Key(long number, int level) {
    }

    private Map<Key, Long> cache;
    private int toCount = 0;

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2024/day11/puzzle1.txt");
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

        List<Long> in = StringConverter.toLongLinkedList(eventData.get(0));
        cache = new HashMap<>();
        toCount = 25;

        response = in.stream().mapToLong(v -> cnt(v, 0)).sum();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long cnt(long n, int level) {
        Key k = new Key(n, level);
        if (cache.containsKey(k)) {
            return cache.get(k);
        }

        if (level == toCount) {
            return 1;
        }

        if (n == 0) {
            var r = cnt(1, level + 1);
            cache.put(k, r);
            return r;
        }

        String f = Long.toString(n);
        int len = f.length();
        if (len % 2 == 0) {
            var r = cnt(Long.parseLong(f.substring(0, len / 2)), level + 1) + cnt(Long.parseLong(f.substring(len / 2)), level + 1);
            cache.put(k, r);
            return r;
        }

        var r = cnt(n * 2024, level + 1);
        cache.put(k, r);
        return r;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<Long> in = StringConverter.toLongLinkedList(eventData.get(0));
        cache = new HashMap<>();
        toCount = 75;

        response = in.stream().mapToLong(v -> cnt(v, 0)).sum();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
