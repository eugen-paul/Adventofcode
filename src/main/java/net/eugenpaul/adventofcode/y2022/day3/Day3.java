package net.eugenpaul.adventofcode.y2022.day3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    @Getter
    private long sumOfThePriorities;
    @Getter
    private long sumOfTheGroupsPriorities;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2022/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        sumOfThePriorities = doPuzzle1(eventData);
        sumOfTheGroupsPriorities = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "sumOfThePriorities : " + getSumOfThePriorities());
        logger.log(Level.INFO, () -> "sumOfTheGroupsPriorities : " + getSumOfTheGroupsPriorities());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        return eventData.stream()//
                .mapToInt(this::getRucksackPriority)//
                .sum();
    }

    private int doPuzzle2(List<String> eventData) {
        List<String> group = new LinkedList<>();
        int responsepriority = 0;

        for (var data : eventData) {
            group.add(data);
            if (group.size() == 3) {
                responsepriority += getGroupPriority(group);
                group.clear();
            }
        }
        return responsepriority;
    }

    private int getRucksackPriority(String data) {
        int length = data.length() / 2;
        String first = data.substring(0, length);
        String second = data.substring(length);

        Set<Character> buffer = new HashSet<>();
        for (var c : first.toCharArray()) {
            buffer.add(c);
        }

        for (var c : second.toCharArray()) {
            if (buffer.contains(c)) {
                return getPriority(c);
            }
        }

        throw new IllegalArgumentException();
    }

    private int getGroupPriority(List<String> data) {
        Set<Character> bufferFirst = new HashSet<>();
        for (var c : data.get(0).toCharArray()) {
            bufferFirst.add(c);
        }

        Set<Character> bufferSecond = new HashSet<>();
        for (var c : data.get(1).toCharArray()) {
            if (bufferFirst.contains(c)) {
                bufferSecond.add(c);
            }
        }

        for (var c : data.get(2).toCharArray()) {
            if (bufferSecond.contains(c)) {
                return getPriority(c);
            }
        }

        throw new IllegalArgumentException();
    }

    private int getPriority(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 27;
        }

        throw new IllegalArgumentException();
    }

}
