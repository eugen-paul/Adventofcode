package net.eugenpaul.adventofcode.y2024.day5;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    @AllArgsConstructor
    private class Order {
        int a;
        int b;

        public boolean isOk(Map<Integer, Integer> numbers) {
            return numbers.getOrDefault(a, -1) < numbers.getOrDefault(b, 100);
        }
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2024/day5/puzzle1.txt");
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

        List<Order> orders = parseOrders(eventData);
        List<Map<Integer, Integer>> pages = parsePages(eventData);

        for (var page : pages) {
            boolean ok = orders.stream().allMatch(o -> o.isOk(page));
            if (ok) {
                response += getMiddle(page);
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private List<Order> parseOrders(List<String> eventData) {
        List<Order> resp = new LinkedList<>();
        for (String line : eventData) {
            if (line.isBlank()) {
                break;
            }
            String[] parts = line.split("\\|");
            resp.add(new Order(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
        return resp;
    }

    private List<Map<Integer, Integer>> parsePages(List<String> eventData) {
        List<Map<Integer, Integer>> resp = new LinkedList<>();
        boolean parse = false;
        for (String line : eventData) {
            if (line.isBlank()) {
                parse = true;
                continue;
            }
            if (!parse) {
                continue;
            }
            String[] parts = line.split(",");
            Map<Integer, Integer> l = new HashMap<>();
            for (int i = 0; i < parts.length; i++) {
                l.put(Integer.parseInt(parts[i]), i);
            }
            resp.add(l);
        }
        return resp;
    }

    private int getMiddle(Map<Integer, Integer> data) {
        int l = data.size();
        for (var entry : data.entrySet()) {
            if (entry.getValue() == l / 2) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("No middle");
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<Order> orders = parseOrders(eventData);
        List<Map<Integer, Integer>> pages = parsePages(eventData);
        Map<Integer, Set<Integer>> orderMap = new HashMap<>();
        for (var order : orders) {
            orderMap.computeIfAbsent(order.a, k -> new HashSet<>()).add(order.b);
        }

        for (var page : pages) {
            boolean ok = orders.stream().allMatch(o -> o.isOk(page));
            if (!ok) {
                List<Integer> fixed = fix(page, orderMap);
                response += fixed.get(fixed.size() / 2);
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private List<Integer> fix(Map<Integer, Integer> toFix, Map<Integer, Set<Integer>> orderMap) {
        Comparator<Integer> comparator = (a, b) -> {
            if (orderMap.getOrDefault(a, Set.of()).contains(b)) {
                return -1;
            }
            if (orderMap.getOrDefault(b, Set.of()).contains(a)) {
                return 1;
            }
            throw new IllegalStateException("No order between " + a + " and " + b);
        };
        
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.addAll(toFix.keySet());
        List<Integer> resp = new LinkedList<>();
        while (!priorityQueue.isEmpty()) {
            resp.add(priorityQueue.poll());
        }
        return resp;
    }

}
