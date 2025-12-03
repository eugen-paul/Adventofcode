package net.eugenpaul.adventofcode.y2025.day3;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2025/day3/puzzle1.txt");
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

        for (var d : eventData) {
            long m = 0L;
            response += tt(d, 2, new HashMap<>());
            response += m;
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        for (var d : eventData) {
            long m = 0L;
            for (int i = 0; i < d.length() - 1; i++) {
                for (int k = i + 1; k < d.length(); k++) {
                    m = max(m, (d.charAt(i) - '0') * 10 + d.charAt(k) - '0');
                }
            }
            response += m;
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        for (var d : eventData) {
            response += tt(d, 12, new HashMap<>());
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long tt(String d, int cnt, HashMap<Integer, Long> cache) {
        var key = d.length() * 100 + cnt;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (cnt == 0) {
            return 0;
        }

        if (d.isEmpty()) {
            return 0;
        }

        if (cnt > d.length()) {
            return 0;
        }

        if (cnt == d.length()) {
            return toLong(d);
        }

        long r = 0;

        var ohne = tt(d.substring(1), cnt, cache);
        long mit;
        if (cnt != 1) {
            mit = tt(d.substring(1), cnt - 1, cache);
            mit = toLong(d.charAt(0)) * pow(10, Long.toString(mit).length()) + mit;
        } else {
            mit = toLong(d.charAt(0));
        }

        r = max(ohne, mit);

        cache.put(key, r);

        return r;
    }

}
