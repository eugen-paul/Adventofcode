package net.eugenpaul.adventofcode.y2023.day25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.UnionSetsByRank;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    private record Pair(int first, int second) {
        Pair(int first, int second) {
            this.first = Math.min(first, second);
            this.second = Math.max(first, second);
        }
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private boolean bruteForce = false;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2023/day25/puzzle1.txt");
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
        Set<Pair> connections = new HashSet<>();
        int nodes = load(eventData, connections);
        List<Pair> connList = connections.stream().toList();

        if (bruteForce) {
            return tryBruteForce(nodes, connList);
        }

        Map<Integer, List<Integer>> connMap = new HashMap<>();
        for (Pair conn : connList) {
            connMap.computeIfAbsent(conn.first, k -> new ArrayList<>()).add(conn.second);
            connMap.computeIfAbsent(conn.second, k -> new ArrayList<>()).add(conn.first);
        }

        int start = 0;
        int set1 = 1;
        int set2 = 0;

        for (int end = 1; end < nodes; end++) {
            Map<Integer, List<Integer>> connMapCopy = new HashMap<>();
            for (Map.Entry<Integer, List<Integer>> entry : connMap.entrySet()) {
                connMapCopy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }

            // find all unique ways from start to end
            Set<Integer> visited = new HashSet<>();
            visited.add(start);

            List<LinkedList<Integer>> toCheck = new LinkedList<>();

            List<Integer> conns = connMapCopy.get(start);
            for (Integer c : conns) {
                LinkedList<Integer> w = new LinkedList<>();
                w.add(start);
                w.add(c);
                toCheck.add(w);
            }

            boolean found = false;
            LinkedList<Integer> ww = new LinkedList<>();

            while (!toCheck.isEmpty()) {
                LinkedList<Integer> way = toCheck.remove(0);
                // if (visited.contains(way.getLast())) {
                // continue;
                // }
                // visited.add(way.getLast());
                if (way.getLast() == end) {
                    found = true;
                    ww = way;
                    break;
                }
                List<Integer> nextConns = connMapCopy.get(way.getLast());
                for (Integer nc : nextConns) {
                    if (visited.contains(nc)) {
                        continue;
                    }
                    visited.add(nc);
                    LinkedList<Integer> newWay = new LinkedList<>(way);
                    newWay.add(nc);
                    toCheck.add(newWay);
                }
            }

            if (!found) {
                throw new RuntimeException("No way found from " + start + " to " + end);
            }
            int last = start;
            for (int i = 1; i < ww.size(); i++) {
                Integer curr = ww.get(i);
                connMapCopy.get(last).remove(curr);
                last = curr;
            }

            // find all unique ways from start to end
            visited = new HashSet<>();
            visited.add(start);

            toCheck = new LinkedList<>();

            conns = connMapCopy.get(start);
            for (Integer c : conns) {
                LinkedList<Integer> w = new LinkedList<>();
                w.add(start);
                w.add(c);
                toCheck.add(w);
            }

            found = false;
            ww = new LinkedList<>();

            while (!toCheck.isEmpty()) {
                LinkedList<Integer> way = toCheck.remove(0);
                // if (visited.contains(way.getLast())) {
                // continue;
                // }
                // visited.add(way.getLast());
                if (way.getLast() == end) {
                    found = true;
                    ww = way;
                    break;
                }
                List<Integer> nextConns = connMapCopy.get(way.getLast());
                for (Integer nc : nextConns) {
                    if (visited.contains(nc)) {
                        continue;
                    }
                    visited.add(nc);
                    LinkedList<Integer> newWay = new LinkedList<>(way);
                    newWay.add(nc);
                    toCheck.add(newWay);
                }
            }

            if (!found) {
                throw new RuntimeException("No way found from " + start + " to " + end);
            }
            last = start;
            for (int i = 1; i < ww.size(); i++) {
                Integer curr = ww.get(i);
                connMapCopy.get(last).remove(curr);
                last = curr;
            }

            // find all unique ways from start to end
            visited = new HashSet<>();
            visited.add(start);

            toCheck = new LinkedList<>();

            conns = connMapCopy.get(start);
            for (Integer c : conns) {
                LinkedList<Integer> w = new LinkedList<>();
                w.add(start);
                w.add(c);
                toCheck.add(w);
            }

            found = false;
            ww = new LinkedList<>();

            while (!toCheck.isEmpty()) {
                LinkedList<Integer> way = toCheck.remove(0);
                // if (visited.contains(way.getLast())) {
                // continue;
                // }
                // visited.add(way.getLast());
                if (way.getLast() == end) {
                    found = true;
                    ww = way;
                    break;
                }
                List<Integer> nextConns = connMapCopy.get(way.getLast());
                for (Integer nc : nextConns) {
                    if (visited.contains(nc)) {
                        continue;
                    }
                    visited.add(nc);
                    LinkedList<Integer> newWay = new LinkedList<>(way);
                    newWay.add(nc);
                    toCheck.add(newWay);
                }
            }

            if (!found) {
                throw new RuntimeException("No way found from " + start + " to " + end);
            }
            last = start;
            for (int i = 1; i < ww.size(); i++) {
                Integer curr = ww.get(i);
                connMapCopy.get(last).remove(curr);
                last = curr;
            }

            // find all unique ways from start to end
            visited = new HashSet<>();
            visited.add(start);

            toCheck = new LinkedList<>();

            conns = connMapCopy.get(start);
            for (Integer c : conns) {
                LinkedList<Integer> w = new LinkedList<>();
                w.add(start);
                w.add(c);
                toCheck.add(w);
            }

            found = false;
            ww = new LinkedList<>();

            while (!toCheck.isEmpty()) {
                LinkedList<Integer> way = toCheck.remove(0);
                // if (visited.contains(way.getLast())) {
                // continue;
                // }
                // visited.add(way.getLast());
                if (way.getLast() == end) {
                    found = true;
                    ww = way;
                    break;
                }
                List<Integer> nextConns = connMapCopy.get(way.getLast());
                for (Integer nc : nextConns) {
                    if (visited.contains(nc)) {
                        continue;
                    }
                    visited.add(nc);
                    LinkedList<Integer> newWay = new LinkedList<>(way);
                    newWay.add(nc);
                    toCheck.add(newWay);
                }
            }

            if (!found) {
                set2++;
            } else {
                set1++;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + ((long) set1 * set2));
        return (long) set1 * set2;
    }

    private long tryBruteForce(int nodes, List<Pair> connList) {
        long response;
        // Brute force
        for (int c1 = 0; c1 < connList.size(); c1++) {
            for (int c2 = c1 + 1; c2 < connList.size(); c2++) {
                for (int c3 = c1 + 2; c3 < connList.size(); c3++) {
                    response = twoGroups(connList, nodes, connList.get(c1), connList.get(c2), connList.get(c3));
                    if (response > 0) {
                        logger.log(Level.INFO, "Solution 1 " + response);
                        return response;
                    }
                }
                System.out.println("Completed c2=" + c2);
            }
            System.out.println("Completed c1=" + c1);
        }
        throw new RuntimeException("No solution found");
    }

    private long twoGroups(List<Pair> connections, int nodes, Pair c1, Pair c2, Pair c3) {
        UnionSetsByRank uf = new UnionSetsByRank(nodes);
        for (Pair conn : connections) {
            if (conn.equals(c1) || conn.equals(c2) || conn.equals(c3)) {
                continue;
            }
            uf.union(conn.first, conn.second);
        }
        if (uf.getComponents() != 2) {
            return 0;
        }
        Map<Integer, Integer> groups = new HashMap<>();
        for (int i = 0; i < nodes; i++) {
            Integer g = uf.find(i);
            groups.compute(g, (key, val) -> (val == null) ? 1 : val + 1);
        }
        if (groups.size() == 2) {
            return groups.values().stream().reduce(1, (a, b) -> a * b);
        }
        return 0;
    }

    private int load(List<String> eventData, Set<Pair> connections) {
        Map<String, Integer> nodeIndex = new HashMap<>();
        int index = 0;

        for (String line : eventData) {
            String[] parts = line.split(":");
            String key = parts[0].trim();
            String[] values = parts[1].trim().split(" ");

            if (!nodeIndex.containsKey(key)) {
                nodeIndex.put(key, index++);
            }

            for (String value : values) {
                if (!nodeIndex.containsKey(value)) {
                    nodeIndex.put(value, index++);
                }
                String[] s = sort(key, value);
                connections.add(new Pair(nodeIndex.get(s[0]), nodeIndex.get(s[1])));
            }
        }
        return index;
    }

    private String conn(String s1, String s2) {
        String[] pair = sort(s1, s2);
        return pair[0] + "+" + pair[1];
    }

    private String[] sort(String s1, String s2) {
        String[] resp = new String[2];
        if (s1.compareTo(s2) <= 0) {
            resp[0] = s1;
            resp[1] = s2;
        } else {
            resp[0] = s2;
            resp[1] = s1;
        }
        return resp;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
