package net.eugenpaul.adventofcode.y2020.day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day15 extends SolutionTemplate {

    @AllArgsConstructor
    @Data
    private class LastRounds {
        private Integer last;
        private Integer beforeLast;
    }

    @Getter
    private long numberAt2020;
    @Getter
    private long numberAt30000000;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2020/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        List<Integer> initialNumbers = Arrays.stream(eventData.split(","))//
                .map(Integer::parseInt)//
                .collect(Collectors.toList());

        numberAt2020 = doPuzzle(initialNumbers, 2020);
        numberAt30000000 = doPuzzle(initialNumbers, 30000000);

        logger.log(Level.INFO, () -> "numberAt2020   : " + getNumberAt2020());
        logger.log(Level.INFO, () -> "numberAt30000000   : " + getNumberAt30000000());

        return true;
    }

    private int doPuzzle(List<Integer> initialNumbers, int rounds) {
        Map<Integer, LastRounds> numberToLastRound = new HashMap<>();

        int lastnumber = 0;
        LastRounds lastRound = new LastRounds(null, null);
        for (int i = 0; i < initialNumbers.size(); i++) {
            lastnumber = initialNumbers.get(i);
            lastRound = new LastRounds(i + 1, null);
            numberToLastRound.put(lastnumber, lastRound);
        }

        for (int currentRound = initialNumbers.size() + 1; currentRound <= rounds; currentRound++) {
            if (lastRound.getBeforeLast() == null) {
                lastnumber = 0;
            } else {
                lastnumber = lastRound.getLast() - lastRound.getBeforeLast();
            }

            LastRounds lastRoundOfCurrent = numberToLastRound.get(lastnumber);
            if (lastRoundOfCurrent == null) {
                lastRound = new LastRounds(currentRound, null);
                numberToLastRound.put(lastnumber, lastRound);
            } else {
                lastRound = lastRoundOfCurrent;
                lastRoundOfCurrent.setBeforeLast(lastRoundOfCurrent.getLast());
                lastRoundOfCurrent.setLast(currentRound);
            }
        }

        return lastnumber;
    }

}
