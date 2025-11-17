package net.eugenpaul.adventofcode.y2023.day8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.ConvertHelper;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day8 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2023/day8/puzzle1.txt");
    }

    @AllArgsConstructor
    private static class Node {
        String name;
        String left;
        String right;

        public Node(String data) {
            // AAA = (BBB, CCC)
            name = data.substring(0, 3);
            left = data.substring(7, 10);
            right = data.substring(12, 15);
        }
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long resp = 0;

        Map<String, List<String>> allNodes = new HashMap<>();
        String way = eventData.get(0);
        for (var node : ConvertHelper.asListList(eventData).get(1)) {
            // AAA = (BBB, CCC)
            allNodes.put(node.substring(0, 3), List.of(node.substring(7, 10), node.substring(12, 15)));
        }

        String cur = "AAA";
        while (!cur.equals("ZZZ")) {
            var w = allNodes.get(cur);
            if (way.charAt((int) (resp % way.length())) == 'L') {
                cur = w.get(0);
            } else {
                cur = w.get(1);
            }
            resp++;
        }

        logger.info("Solution 1: " + resp);
        return resp;
    }

    public long doPuzzle1_a(List<String> eventData) {
        char[] way = eventData.get(0).toCharArray();
        Map<String, Node> nodes = readNodes(eventData);

        int pos = 0;
        String posNode = "AAA";
        do {
            char r = way[pos % way.length];
            pos++;
            if (r == 'L') {
                posNode = nodes.get(posNode).left;
            } else {
                posNode = nodes.get(posNode).right;
            }
        } while (!posNode.equals("ZZZ"));
        return pos;
    }

    public long doPuzzle2(List<String> eventData) {
        Map<String, List<String>> allNodes = new HashMap<>();
        List<String> allStarts = new ArrayList<>();
        String way = eventData.get(0);
        for (var node : ConvertHelper.asListList(eventData).get(1)) {
            // AAA = (BBB, CCC)
            String s = node.substring(0, 3);
            allNodes.put(s, List.of(node.substring(7, 10), node.substring(12, 15)));
            if (s.endsWith("A")) {
                allStarts.add(s);
            }
        }

        long resp = 1;

        for (int i = 0; i < allStarts.size(); i++) {
            long step = 0;
            String cur = allStarts.get(i);
            while (!cur.endsWith("Z")) {
                var w = allNodes.get(cur);
                if (way.charAt((int) (step % way.length())) == 'L') {
                    cur = w.get(0);
                } else {
                    cur = w.get(1);
                }
                step++;
            }

            long step2 = step;
            if (way.charAt((int) (step2 % way.length())) == 'L') {
                cur = allNodes.get(cur).get(0);
            } else {
                cur = allNodes.get(cur).get(1);
            }
            step2++;

            while (!cur.endsWith("Z")) {
                var w = allNodes.get(cur);
                if (way.charAt((int) (step2 % way.length())) == 'L') {
                    cur = w.get(0);
                } else {
                    cur = w.get(1);
                }
                step2++;
            }

            if (step2 == step * 2) {
                resp = MathHelper.lcm(resp, step);
            } else {
                throw new IllegalArgumentException("What to do?");
            }
        }

        logger.info("Solution 2: " + resp);
        return resp;
    }

    public long doPuzzle2_a(List<String> eventData) {
        char[] way = eventData.get(0).toCharArray();
        Map<String, Node> nodes = readNodes(eventData);
        List<String> startNodes = nodes.keySet().stream().filter(n -> n.endsWith("A")).toList();
        Set<String> endNodes = nodes.keySet().stream().filter(n -> n.endsWith("Z")).collect(Collectors.toSet());

        long response = 1;
        for (String start : startNodes) {
            long pos = 0;
            String posNode = start;
            do {
                char r = way[(int) pos % way.length];
                pos++;
                if (r == 'L') {
                    posNode = nodes.get(posNode).left;
                } else {
                    posNode = nodes.get(posNode).right;
                }
            } while (!endNodes.contains(posNode));
            response = MathHelper.lcm(response, pos);
        }

        return response;
    }

    private Map<String, Node> readNodes(List<String> eventData) {
        Map<String, Node> nodes = new HashMap<>();
        for (int i = 2; i < eventData.size(); i++) {
            Node node = new Node(eventData.get(i));
            nodes.put(node.name, node);
        }
        return nodes;
    }
}
