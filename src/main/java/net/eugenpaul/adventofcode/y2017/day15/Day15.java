package net.eugenpaul.adventofcode.y2017.day15;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day15 extends SolutionTemplate {

    private long factorA = 16807;
    private long factorB = 48271;

    @Getter
    private Integer judge;
    @Getter
    private Integer judge2;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2017/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        long genA = Long.parseLong(eventData.get(0).substring(24));
        long genB = Long.parseLong(eventData.get(1).substring(24));

        judge = duPuzzle1(genA, genB);
        logger.log(Level.INFO, () -> "judge : " + getJudge());

        judge2 = duPuzzle2(genA, genB);
        logger.log(Level.INFO, () -> "judge2 : " + getJudge2());

        return true;
    }

    private int duPuzzle1(long genA, long genB) {
        int responseJudge = 0;
        long currentA = genA;
        long currentB = genB;
        for (int i = 0; i < 40_000_000; i++) {
            currentA = getNext(currentA, factorA);
            currentB = getNext(currentB, factorB);

            if (isLowest16BitEqual(currentA, currentB)) {
                responseJudge++;
            }
        }

        return responseJudge;
    }

    private int duPuzzle2(long genA, long genB) {
        int responseJudge = 0;
        long currentA = genA;

        long currentB = genB;
        for (int i = 0; i < 5_000_000; i++) {
            do {
                currentA = getNext(currentA, factorA);
            } while ((currentA & 0x3) != 0);

            do {
                currentB = getNext(currentB, factorB);
            } while ((currentB & 0x7) != 0);

            if (isLowest16BitEqual(currentA, currentB)) {
                responseJudge++;
            }
        }

        return responseJudge;
    }

    private boolean isLowest16BitEqual(long a, long b) {
        return ((int) a & 0xFFFF) == ((int) b & 0xFFFF);
    }

    private long getNext(long current, long factor) {
        return (current * factor) % 2147483647L;
    }

}
