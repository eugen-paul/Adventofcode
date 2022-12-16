package net.eugenpaul.adventofcode.y2022.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.DijkstraGen;
import net.eugenpaul.adventofcode.helper.dijkstra.MazeGen;

public class Day16 extends SolutionTemplate {

    @AllArgsConstructor
    private class Point {
        private SimplePos pos;
        private String name;
        private int rate;
        private boolean isOpen;
        private List<String> neighbors;

        public Point copy() {
            return new Point(pos, name, rate, isOpen, neighbors);
        }
    }

    @AllArgsConstructor
    private class Area implements MazeGen<SimplePos> {

        Map<String, Point> mazeArea;

        @Override
        public List<SimplePos> getNextSteps(SimplePos from) {
            List<SimplePos> response = new LinkedList<>();

            var e = mazeArea.entrySet().stream().filter(v -> v.getValue().pos.equals(from)).findFirst().orElseThrow();

            for (var neighbor : e.getValue().neighbors) {
                response.add(mazeArea.get(neighbor).pos);
            }

            return response;
        }

    }

    @Getter
    private long part1;
    @Getter
    private long part2;

    @Setter
    private boolean printArea = false;

    private Map<String, Map<String, Integer>> dijkstraBuffer;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2022/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        var area = readScan(eventData);
        return computePressure(area, "AA", 30);
    }

    private long doPuzzle2(List<String> eventData) {
        var area = readScan(eventData);

        int countOpen = (int) area.values().stream()//
                .filter(v -> v.rate > 0)//
                .count();

        Map<Integer, String> tt = new HashMap<>();

        int count = 0;
        for (var entry : area.entrySet()) {
            if (entry.getValue().rate > 0) {
                tt.put(count, entry.getKey());
                count++;
            }
        }

        int pow = (int) Math.pow(2, count - 1);

        int maxRespone = 0;

        Set<String> mySet = new HashSet<>();
        Set<String> elSet = new HashSet<>();
        for (int i = 1; i < pow; i++) {
            mySet.clear();
            elSet.clear();

            mySet.add(tt.get(count - 1));
            int mod = i;
            for (int j = 0; j < count - 1; j++) {
                if (mod % 2 == 0) {
                    mySet.add(tt.get(j));
                } else {
                    elSet.add(tt.get(j));
                }
                mod /= 2;
            }

            Map<String, Point> myArea = copyArea(area);
            Map<String, Point> elArea = copyArea(area);

            mySet.forEach(v -> elArea.get(v).rate = 0);
            elSet.forEach(v -> myArea.get(v).rate = 0);

            // I assume that the number of nodes visited by me and elephant are about the same.
            if (elSet.size() < countOpen / 2 - 1 || mySet.size() < countOpen / 2 - 1) {
                logger.log(Level.FINEST, "skip step: {0} ", i);
                continue;
            }

            maxRespone = Math.max(//
                    maxRespone, //
                    computePressure(myArea, "AA", 26) + computePressure(elArea, "AA", 26) //
            );

            logger.log(Level.FINEST, "step: {0} maxRespone: {1}", new Object[] { i, maxRespone });
        }

        return maxRespone;
    }

    private int computePressure(Map<String, Point> area, String position, int limit) {
        Map<String, Integer> hMap = new HashMap<>();
        dijkstraBuffer = new HashMap<>();
        return doStepsFaster(area, 1, position, limit, hMap);
    }

    private int doStepsFaster(Map<String, Point> area, int minute, String position, int limit, Map<String, Integer> hMap) {
        if (minute == limit + 1) {
            return 0;
        }

        int releaseAfterMinute = 0;

        boolean isAllOpen = area.entrySet().stream()//
                .filter(v -> v.getValue().rate > 0)//
                .allMatch(v -> v.getValue().isOpen);

        int rateProMinute = 0;
        for (var entry : area.entrySet()) {
            if (entry.getValue().isOpen) {
                rateProMinute += entry.getValue().rate;
            }
        }

        if (isAllOpen) {
            // All valves are already open. Calculate result.
            releaseAfterMinute += rateProMinute * (limit + 1 - minute);
            return releaseAfterMinute;
        }

        releaseAfterMinute += rateProMinute;

        int responseRelease = releaseAfterMinute;

        if (!area.get(position).isOpen && area.get(position).rate != 0) {
            // if you stand on a node that is not open, open it.
            var areaCopy = copyArea(area);
            areaCopy.get(position).isOpen = true;
            return Math.max(responseRelease, releaseAfterMinute + doStepsFaster(areaCopy, minute + 1, position, limit, hMap));
        }

        var toOpen = area.entrySet().stream()//
                .filter(v -> !v.getValue().isOpen && v.getValue().rate > 0)//
                .map(Entry::getKey)//
                .collect(Collectors.toList())//
        ;

        // If the input data has already been handled, use the data.
        String hashPrefix = toOpen.stream().sorted().reduce(position + ":", (a, b) -> a + b);
        String hash = hashPrefix + minute;
        if (hMap.containsKey(hash)) {
            return hMap.get(hash);
        }

        for (var target : toOpen) {
            var myMap = new Area(area);

            var targetCost = dijkstraBuffer.computeIfAbsent(position, k -> new HashMap<>())//
                    .get(target);

            if (targetCost == null) {
                // the implementation of Dijkstra is to slow. Thats way the data should be cached.
                var way = DijkstraGen.getPath(myMap, area.get(position).pos, area.get(target).pos);
                targetCost = way.size();
                dijkstraBuffer.get(position).put(target, targetCost);
            }

            if (targetCost < limit + 1 - minute) {
                var areaCopy = copyArea(area);
                responseRelease = Math.max(//
                        responseRelease, //
                        releaseAfterMinute + rateProMinute * (targetCost - 1) + doStepsFaster(areaCopy, minute + targetCost, target, limit, hMap)//
                );
            } else {
                int restMinutes = limit - minute;
                responseRelease = Math.max(responseRelease, rateProMinute * restMinutes + releaseAfterMinute);
            }
        }

        hMap.put(hash, responseRelease);

        return responseRelease;
    }

    @SuppressWarnings("unused")
    private int doStepsSlow(Map<String, Point> area, int minute, String position, int currentPressure, int limit, Map<String, Integer> hMap) {
        if (minute == limit + 1) {
            return currentPressure;
        }

        int releaseAfterMinute = currentPressure;

        boolean isAllOpen = area.entrySet().stream()//
                .filter(v -> v.getValue().rate > 0)//
                .allMatch(v -> v.getValue().isOpen);

        int rateProMinute = 0;
        for (var entry : area.entrySet()) {
            if (entry.getValue().isOpen) {
                rateProMinute += entry.getValue().rate;
            }
        }

        if (isAllOpen) {
            releaseAfterMinute += rateProMinute * (limit + 1 - minute);
            return releaseAfterMinute;
        }

        releaseAfterMinute += rateProMinute;

        int responseRelease = releaseAfterMinute;

        if (!area.get(position).isOpen && area.get(position).rate != 0) {
            var areaCopy = copyArea(area);
            areaCopy.get(position).isOpen = true;
            return Math.max(responseRelease, doStepsSlow(areaCopy, minute + 1, position, releaseAfterMinute, limit, hMap));
        }

        var toOpen = area.entrySet().stream()//
                .filter(v -> !v.getValue().isOpen && v.getValue().rate > 0)//
                .map(Entry::getKey)//
                .collect(Collectors.toList())//
        ;

        for (var neightbar : toOpen) {
            var myMap = new Area(area);
            var way = DijkstraGen.getPath(myMap, area.get(position).pos, area.get(neightbar).pos);

            if (way.size() < limit + 1 - minute) {
                var areaCopy = copyArea(area);
                responseRelease = Math.max(//
                        responseRelease, //
                        doStepsSlow(areaCopy, minute + way.size(), neightbar, releaseAfterMinute + rateProMinute * (way.size() - 1), limit, hMap)//
                );
            } else {
                int restMinutes = limit - minute;
                responseRelease = Math.max(responseRelease, rateProMinute * restMinutes + releaseAfterMinute);
            }
        }

        return responseRelease;
    }

    private Map<String, Point> copyArea(Map<String, Point> area) {
        Map<String, Point> areaCopy = new HashMap<>();
        for (var entry : area.entrySet()) {
            areaCopy.put(entry.getKey(), entry.getValue().copy());
        }
        return areaCopy;
    }

    private Map<String, Point> readScan(List<String> eventData) {
        // Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Map<String, Point> response = new HashMap<>();

        int x = 0;

        for (var data : eventData) {
            var splits = data.split(" ");

            String name = splits[1];
            int rate = Integer.parseInt(splits[4].replace("rate=", "").replace(";", ""));
            boolean isOpen = false;
            List<String> neightbars = new LinkedList<>();
            for (int i = 9; i < splits.length; i++) {
                neightbars.add(splits[i].replace(",", ""));
            }
            response.put(//
                    name, //
                    new Point(new SimplePos(x, 0), name, rate, isOpen, neightbars)//
            );
            x++;
        }

        return response;
    }
}
