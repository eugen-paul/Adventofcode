package net.eugenpaul.adventofcode.y2018.day1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private long resultingFrequency;
    @Getter
    private long firstTwice;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2018/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var intData = eventData.stream().map(Integer::parseInt).collect(Collectors.toList());

        resultingFrequency = intData.stream().reduce(0, (a, b) -> a + b);
        firstTwice = doPuzzle2(intData);

        logger.log(Level.INFO, () -> "resultingFrequency: " + getResultingFrequency());
        logger.log(Level.INFO, () -> "firstTwice: " + getFirstTwice());

        return true;
    }

    private int doPuzzle2(List<Integer> intData) {
        Map<Integer, Boolean> memory = new HashMap<>();
        int currentFrequency = 0;
        memory.put(currentFrequency, true);

        boolean found = false;
        while (!found) {
            for (Integer delta : intData) {
                currentFrequency += delta;
                if (memory.containsKey(currentFrequency)) {
                    found = true;
                    break;
                } else {
                    memory.put(currentFrequency, true);
                }
            }
        }

        return currentFrequency;
    }

}
