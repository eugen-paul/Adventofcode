package net.eugenpaul.adventofcode.y2018.day5;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day5 extends SolutionTemplate {

    @Getter
    private long unitsAfterReacting;
    @Getter
    private long shortestPolymer;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2018/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        unitsAfterReacting = reduceUnits(StringConverter.toCharStream(eventData).collect(Collectors.toCollection(LinkedList::new)));

        int bestLength = Integer.MAX_VALUE;
        for (char i = 'a'; i <= 'z'; i++) {
            char filter = i;
            LinkedList<Character> units = StringConverter.toCharStream(eventData)//
                    .filter(v -> Character.toLowerCase(v) != filter)//
                    .collect(Collectors.toCollection(LinkedList::new));

            int currentLength = reduceUnits(units);

            if (currentLength < bestLength) {
                bestLength = currentLength;
            }
        }
        shortestPolymer = bestLength;

        logger.log(Level.INFO, () -> "unitsAfterReacting  : " + getUnitsAfterReacting());
        logger.log(Level.INFO, () -> "shortestPolymer  : " + getShortestPolymer());

        return true;
    }

    private int reduceUnits(LinkedList<Character> units) {

        var iterator = units.listIterator();
        Character lastUnit = null;
        while (iterator.hasNext()) {
            if (lastUnit != null) {
                Character currentUnit = iterator.next();
                if (Character.toLowerCase(lastUnit) != Character.toLowerCase(currentUnit)//
                        || lastUnit.equals(currentUnit)//
                ) {
                    lastUnit = currentUnit;
                    continue;
                }

                iterator.remove();
                iterator.previous();
                iterator.remove();

                lastUnit = null;
                if (iterator.hasPrevious()) {
                    lastUnit = iterator.previous();
                    if (iterator.hasNext()) {
                        iterator.next();
                    }
                }
            } else {
                lastUnit = iterator.next();
            }
        }
        return units.size();
    }

}
