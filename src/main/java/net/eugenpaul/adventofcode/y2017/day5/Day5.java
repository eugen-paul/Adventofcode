package net.eugenpaul.adventofcode.y2017.day5;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    @Getter
    private int steps;
    @Getter
    private int steps2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2017/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        List<Integer> jumps = eventData.stream()//
                .mapToInt(Integer::parseInt)//
                .boxed()//
                .collect(Collectors.toCollection(LinkedList::new));

        steps = doPuzzle(jumps, false);

        jumps = eventData.stream()//
                .mapToInt(Integer::parseInt)//
                .boxed()//
                .collect(Collectors.toCollection(LinkedList::new));

        steps2 = doPuzzle(jumps, true);

        logger.log(Level.INFO, () -> "steps : " + getSteps());
        logger.log(Level.INFO, () -> "steps2 : " + getSteps2());

        return true;
    }

    private int doPuzzle(List<Integer> jumps, boolean doDecrease) {
        int responseSteps = 0;
        var iterator = jumps.listIterator();
        try {
            while (iterator.hasNext()) {
                var offset = iterator.next();

                if (doDecrease && offset >= 3) {
                    iterator.set(offset - 1);
                } else {
                    iterator.set(offset + 1);
                }

                if (offset > 0) {
                    for (int i = 0; i < offset - 1; i++) {
                        iterator.next();
                    }
                } else {
                    for (int i = offset; i <= 0; i++) {
                        iterator.previous();
                    }
                }
                responseSteps++;
            }
        } catch (NoSuchElementException e) {
            // done
        }

        return responseSteps;
    }

}
