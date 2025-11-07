package net.eugenpaul.adventofcode.y2024.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2024/day22/puzzle1.txt");
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

        List<Long> data = eventData.stream().map(Long::parseLong).toList();

        for (long in : data) {
            long r = in;
            for (int i = 0; i < 2000; i++) {
                r = step(r);
            }
            response += r;
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long step(long in) {
        long sc = in;
        long tmp = sc * 64;
        tmp = tmp ^ sc;
        sc = tmp % 16777216L;

        tmp = sc >>> 5;
        tmp = tmp ^ sc;
        sc = tmp % 16777216L;

        tmp = tmp << 11;
        tmp = tmp ^ sc;
        sc = tmp % 16777216L;
        return sc;
    }

    public long doPuzzle2(List<String> eventData) {
        long response;

        List<Long> data = eventData.stream().map(Long::parseLong).toList();
        Map<String, Integer> seqToPrize = new HashMap<>();

        for (long in : data) {
            long r = in;
            Set<String> sTp = new HashSet<>();
            LinkedList<String> seq = new LinkedList<>();
            for (int i = 0; i < 2000; i++) {
                long old = r;
                r = step(r);
                int d = (int) (r % 10 - old % 10);
                seq.add(Integer.toString(d));
                if (seq.size() >= 4) {
                    String changes = String.join(",", seq);
                    seq.pollFirst();
                    int pr = (int) (r % 10);
                    if (!sTp.contains(changes)) {
                        seqToPrize.compute(changes, (k, v) -> v == null ? pr : v + pr);
                        sTp.add(changes);
                    }
                }
            }
        }

        response = seqToPrize.values().stream().mapToInt(v -> v).max().orElseThrow();
        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_slow(List<String> eventData) {
        long response;

        List<Long> data = eventData.stream().map(Long::parseLong).toList();
        List<Map<String, Integer>> seqToPrize = new ArrayList<>();

        for (long in : data) {
            long r = in;
            Map<String, Integer> sTp = new HashMap<>();
            LinkedList<String> seq = new LinkedList<>();
            for (int i = 0; i < 2000; i++) {
                long old = r;
                r = step(r);
                int d = (int) (r % 10 - old % 10);
                seq.add(Integer.toString(d));
                if (seq.size() >= 4) {
                    String changes = String.join(",", seq);
                    seq.pollFirst();
                    int pr = (int) (r % 10);
                    sTp.computeIfAbsent(changes, k -> pr);
                }
            }
            seqToPrize.add(sTp);
        }

        Set<String> allDifs = new HashSet<>();
        for (Map<String, Integer> s : seqToPrize) {
            allDifs.addAll(s.keySet());
        }

        response = Long.MIN_VALUE;

        for (String d : allDifs) {
            long dCost = seqToPrize.stream()//
                    .map(v -> (long) (v.getOrDefault(d, 0)))//
                    .reduce(0L, (a, b) -> a + b);
            if (response < dCost) {
                response = dCost;
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
