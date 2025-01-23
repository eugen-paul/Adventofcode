package net.eugenpaul.adventofcode.y2023.day5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2023/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        Set<Long> seeds = new HashSet<>();
        Map<String, TreeMap<Long, List<Long>>> convertMap = new HashMap<>();
        Map<String, String> fromTo = new HashMap<>();

        boolean first = true;
        boolean readNames = false;
        String name = "";
        for (String line : eventData) {
            if (first) {
                seeds = Stream.of(line.split(" ")).filter(s -> !s.startsWith("seeds")).map(Long::parseLong).collect(Collectors.toSet());
                first = false;
            } else {
                if (line.isBlank()) {
                    readNames = true;
                    continue;
                }
                if (readNames) {
                    var n = line.split(" ")[0].split("-");
                    name = n[0] + " " + n[2];
                    fromTo.put(n[0], n[2]);
                    readNames = false;
                } else {
                    var d = Stream.of(line.split(" ")).map(Long::parseLong).toList();
                    convertMap.computeIfAbsent(name, k -> new TreeMap<>()).put(d.get(1), List.of(d.get(0), d.get(2)));
                }
            }
        }

        Long minLoc = Long.MAX_VALUE;
        String pos = "seed";
        for (Long seed : seeds) {
            StringBuilder logMsg = new StringBuilder("Seed: " + seed + " ");
            Long curPos = seed;
            String dest = "";

            do {
                dest = fromTo.get(pos);
                name = pos + " " + dest;
                var targets = convertMap.get(name);
                var tt = targets.floorEntry(curPos);
                if (tt == null) {
                    tt = Map.entry(0L, List.of(0L, 0L));
                }
                var sourse = tt.getKey();
                var tar = tt.getValue().get(0);
                var range = tt.getValue().get(1);
                if (sourse + range > curPos) {
                    curPos = tar + curPos - sourse;
                }
                pos = dest;
                logMsg.append(curPos + " ");
            } while (!dest.equals("location"));
            minLoc = Math.min(minLoc, curPos);
            pos = "seed";
            logger.info("Solution 1: " + logMsg.toString());
        }

        return minLoc;
    }

    public long doPuzzle2(List<String> eventData) {
        List<Long> seeds = new ArrayList<>();
        Map<String, TreeMap<Long, List<Long>>> convertMap = new HashMap<>();
        Map<String, String> fromTo = new HashMap<>();

        boolean first = true;
        boolean readNames = false;
        String name = "";
        for (String line : eventData) {
            if (first) {
                seeds = Stream.of(line.split(" ")).filter(s -> !s.startsWith("seeds")).map(Long::parseLong).toList();
                first = false;
            } else {
                if (line.isBlank()) {
                    readNames = true;
                    continue;
                }
                if (readNames) {
                    var n = line.split(" ")[0].split("-");
                    name = n[0];
                    fromTo.put(n[0], n[2]);
                    readNames = false;
                } else {
                    var d = Stream.of(line.split(" ")).map(Long::parseLong).toList();
                    convertMap.computeIfAbsent(name, k -> new TreeMap<>()).put(d.get(1), List.of(d.get(0), d.get(2)));
                }
            }
        }

        AtomicLong minLoc = new AtomicLong(Long.MAX_VALUE);
        Object lock = new Object();

        try (ExecutorService executor = Executors.newFixedThreadPool(6)) {
            for (int i = 0; i < seeds.size(); i += 2) {
                Long start = seeds.get(i);
                Long end = seeds.get(i + 1) + start;

                for (long p = start; p < end; p += 200_000_000L) {
                    Long s = p;
                    Long e = Math.min(p + 200_000_000L, end);
                    executor.submit(() -> {
                        logger.info("Start Thread: start " + s + " end " + e);
                        long localMin = step(s, e, fromTo, convertMap);
                        synchronized (lock) {
                            if (minLoc.get() > localMin) {
                                minLoc.set(localMin);
                            }
                        }
                    });
                }
            }

            executor.shutdown();
        }

        logger.info("Solution 2: " + minLoc.toString());
        return minLoc.get();
    }

    public long step(Long start, Long end, Map<String, String> fromTo, Map<String, TreeMap<Long, List<Long>>> convertMap) {
        var std = Map.entry(0L, List.of(0L, 0L));
        long localMin = Long.MAX_VALUE;
        String pos = "seed";
        for (long seed = start; seed < end; seed++) {
            Long curPos = seed;
            String dest = "";

            do {
                dest = fromTo.get(pos);
                var targets = convertMap.get(pos);
                var tt = targets.floorEntry(curPos);
                if (tt == null) {
                    tt = std;
                }
                var sourse = tt.getKey();
                var tar = tt.getValue().get(0);
                var range = tt.getValue().get(1);
                if (sourse + range > curPos) {
                    curPos = tar + curPos - sourse;
                }
                pos = dest;
            } while (!dest.equals("location"));
            localMin = Math.min(localMin, curPos);
            pos = "seed";
        }
        return localMin;
    }

}
