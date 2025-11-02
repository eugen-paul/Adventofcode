package net.eugenpaul.adventofcode.y2024.day1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2024/day1/puzzle1.txt");
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
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> a - b);
        PriorityQueue<Integer> right = new PriorityQueue<>((a, b) -> a - b);

        for (String line : eventData) {
            String[] pair = line.split("   ");
            left.add(Integer.parseInt(pair[0]));
            right.add(Integer.parseInt(pair[1]));
        }

        //!!!! Don't use iterators to keep the order !!!!
        while (!left.isEmpty() && !right.isEmpty()) {
            Integer l = left.poll();
            Integer r = right.poll();

            if (l == 0 || r == 0) {
                throw new IllegalStateException("Both values are zero");
            }

            response += Math.abs(r - l);
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        List<Integer> left = new ArrayList<>();
        Map<Integer, Integer> cntRight = new HashMap<>();

        for (String line : eventData) {
            String[] pair = line.split("   ");
            left.add(Integer.parseInt(pair[0]));
            Integer r = Integer.parseInt(pair[1]);
            cntRight.compute(r, (k, v) -> v == null ? 1 : v + 1);
        }

        for(var l: left){
            response += cntRight.getOrDefault(l, 0)*l;
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
