package net.eugenpaul.adventofcode.y2023.day9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day9 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2023/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        for (String data : eventData) {
            response += getLast1(StringConverter.toLongArrayList(data));
        }

        logger.info("Solution 1 " + response);
        return response;
    }

    private long getLast1(List<Long> in) {
        Set<Long> diffs = new HashSet<>(in);
        if (diffs.size() == 1 && diffs.contains(0L)) {
            return 0;
        }
        List<Long> nextIn = new ArrayList<>();
        for (int i = 1; i < in.size(); i++) {
            nextIn.add(in.get(i) - in.get(i - 1));
        }
        long lastN = getLast1(nextIn);
        return in.getLast() + lastN;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        for (String data : eventData) {
            response += getLast(StringConverter.toLongArrayList(data));
        }

        logger.info("Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        for (String data : eventData) {
            response += getFirst1(StringConverter.toLongArrayList(data));
        }

        logger.info("Solution 2 " + response);
        return response;
    }

    private long getFirst1(List<Long> in) {
        Set<Long> diffs = new HashSet<>(in);
        if (diffs.size() == 1 && diffs.contains(0L)) {
            return 0;
        }
        List<Long> nextIn = new ArrayList<>();
        for (int i = 1; i < in.size(); i++) {
            nextIn.add(in.get(i) - in.get(i - 1));
        }
        long firstN = getFirst1(nextIn);
        return in.getFirst() - firstN;
    }

    public long doPuzzle2a(List<String> eventData) {
        long response = 0;

        for (String data : eventData) {
            response += getFirst(StringConverter.toLongArrayList(data));
        }

        logger.info("Solution 2 " + response);
        return response;
    }

    private long getLast(List<Long> data) {
        if (data.stream().allMatch(v -> v.longValue() == 0L)) {
            return 0L;
        }

        List<Long> nextLine = new ArrayList<>(data.size());

        for (int i = 0; i < data.size() - 1; i++) {
            nextLine.add(data.get(i + 1) - data.get(i));
        }

        return getLast(nextLine) + data.getLast();
    }

    private long getFirst(List<Long> data) {
        if (data.stream().allMatch(v -> v.longValue() == 0L)) {
            return 0L;
        }

        List<Long> nextLine = new ArrayList<>(data.size());

        for (int i = 0; i < data.size() - 1; i++) {
            nextLine.add(data.get(i + 1) - data.get(i));
        }

        return data.getFirst() - getFirst(nextLine);
    }

}
