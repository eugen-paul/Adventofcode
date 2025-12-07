package net.eugenpaul.adventofcode.y2025.day7;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day7 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2025/day7/puzzle1.txt");
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

        char[][] m = toCharArray(eventData);

        for (int x = 0; x < eventData.get(0).length(); x++) {
            if (m[0][x] == 'S') {
                m[0][x] = '|';
            }
        }

        for (int y = 1; y < eventData.size(); y++) {
            for (int x = 1; x < eventData.get(0).length() - 1; x++) {
                if (m[y - 1][x] == '|') {
                    if (m[y][x] == '^') {
                        m[y][x - 1] = '|';
                        m[y][x + 1] = '|';
                        response++;
                    } else {
                        m[y][x] = '|';
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        var start = StringConverter.posOfChar(eventData, 'S');

        HashSet<SimplePos> seen = new HashSet<>();
        LinkedList<SimplePos> nxt = new LinkedList<>();
        var d = Direction.S;
        nxt.add(start.moveNew(Direction.S));

        while (!nxt.isEmpty()) {
            var cur = nxt.pollFirst();
            if (seen.contains(cur) || !m.containsKey(cur)) {
                continue;
            }
            seen.add(cur);
            if (m.get(cur) == '^') {
                response++;
                nxt.add(cur.moveNew(d).moveNew(Direction.E));
                nxt.add(cur.moveNew(d).moveNew(Direction.W));
            } else {
                nxt.add(cur.moveNew(d));
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }
    
    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        long[] cur = new long[eventData.get(0).length()];
        Arrays.fill(cur, 0);

        long[] last = new long[eventData.get(0).length()];
        Arrays.fill(last, 1);

        int maxX = eventData.get(0).length() - 1;
        int maxY = eventData.size() - 1;

        for (int y = maxY - 1; y > 0; y--) {
            for (int x = 0; x <= maxX; x++) {
                var c = eventData.get(y).charAt(x);
                if (c == '.') {
                    cur[x] = last[x];
                } else {
                    cur[x] = last[x - 1] + last[x + 1];
                }
            }
            var tmp = cur;
            cur = last;
            last = tmp;
        }

        response = Arrays.stream(last).max().orElseThrow();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_b(List<String> eventData) {
        long response = 0;

        long[][] dp = new long[eventData.size()][eventData.get(0).length()];
        for (var row : dp) {
            Arrays.fill(row, 0);
        }

        int maxX = eventData.get(0).length() - 1;
        int maxY = eventData.size() - 1;

        Arrays.fill(dp[maxY], 1);

        for (int y = maxY - 1; y > 0; y--) {
            for (int x = 0; x <= maxX; x++) {
                var c = eventData.get(y).charAt(x);
                if (c == '.') {
                    dp[y][x] = dp[y + 1][x];
                } else {
                    dp[y][x] = dp[y + 1][x - 1] + dp[y + 1][x + 1];
                }
            }
        }

        response = Arrays.stream(dp[1]).max().orElseThrow();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_a(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        var start = StringConverter.posOfChar(eventData, 'S');

        response = 1 + step(m, start.moveNew(Direction.S), new HashMap<>());

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long step(Map<SimplePos, Character> m, SimplePos pos, Map<SimplePos, Long> cache) {
        if (cache.containsKey(pos)) {
            return cache.get(pos);
        }

        if (!m.containsKey(pos)) {
            return 0L;
        }

        long response = 0L;
        if (m.get(pos) == '^') {
            response += step(m, pos.moveNew(Direction.S).moveNew(Direction.E), cache);
            response += step(m, pos.moveNew(Direction.S).moveNew(Direction.W), cache);
            response++;
        } else {
            response += step(m, pos.moveNew(Direction.S), cache);
        }

        cache.put(pos, response);

        return response;
    }

}
