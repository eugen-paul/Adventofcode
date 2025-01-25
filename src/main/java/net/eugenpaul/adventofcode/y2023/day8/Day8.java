package net.eugenpaul.adventofcode.y2023.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
