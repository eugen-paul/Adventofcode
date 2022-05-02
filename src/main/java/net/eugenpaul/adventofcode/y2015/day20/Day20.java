package net.eugenpaul.adventofcode.y2015.day20;

import java.util.function.IntToLongFunction;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day20 extends SolutionTemplate {

    @Getter
    private int lowestHouseNumber;
    @Getter
    private int lowestHouseNumberPart2;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2015/day20/puzzle1.txt");
    }

    @Override
    public boolean doEvent(Integer eventData) {
        lowestHouseNumber = doPuzzle(eventData, this::countPresentsForHouse);
        lowestHouseNumberPart2 = doPuzzle(eventData, this::countPresentsForHousePart2);

        logger.log(Level.INFO, () -> "lowestHouseNumber: " + getLowestHouseNumber());
        logger.log(Level.INFO, () -> "lowestHouseNumberPart2: " + getLowestHouseNumberPart2());
        return true;
    }

    private int doPuzzle(long presentsCount, IntToLongFunction countFunction) {
        int houseNumber = 0;
        long presents = 0;
        do {
            houseNumber++;
            presents = countFunction.applyAsLong(houseNumber);
        } while (presentsCount > presents);
        return houseNumber;
    }

    private Long countPresentsForHouse(Integer houseNumber) {
        long response = 0L;
        long limit = (long) (Math.sqrt(houseNumber));
        for (int i = 1; i <= limit; i++) {
            if (houseNumber % i == 0) {
                response += i;
                long secondMultiplier = houseNumber / i;
                if (secondMultiplier != i) {
                    response += secondMultiplier;
                }
            }
        }
        return response * 10;
    }

    private long countPresentsForHousePart2(int houseNumber) {
        long response = 0;
        long limit = (long) (Math.sqrt(houseNumber));
        for (int i = 1; i <= limit; i++) {
            if (houseNumber % i == 0) {
                long secondMultiplier = houseNumber / i;
                if (secondMultiplier <= 50) {
                    response += i;
                }
                if (secondMultiplier != i && i <= 50) {
                    response += secondMultiplier;
                }
            }
        }
        return response * 11;
    }
}
