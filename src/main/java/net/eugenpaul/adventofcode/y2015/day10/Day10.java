package net.eugenpaul.adventofcode.y2015.day10;

import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day10 extends SolutionTemplate {

    @Setter
    private int steps = 40;
    @Setter
    private int additionalSteps = 10;

    @Getter
    private int lengthPuzzle1;

    @Getter
    private int lengthPuzzle2;

    public static void main(String[] args) {
        Day10 puzzle1 = new Day10();
        puzzle1.doPuzzleFromFile("y2015/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        String result = eventData;

        for (int i = 0; i < steps; i++) {
            result = doStep(result);
        }

        lengthPuzzle1 = result.length();

        for (int i = 0; i < additionalSteps; i++) {
            result = doStep(result);
        }
        lengthPuzzle2 = result.length();

        logger.log(Level.INFO, () -> "the length of the result:     " + getLengthPuzzle1());
        logger.log(Level.INFO, () -> "the length of the new result: " + getLengthPuzzle2());

        return true;
    }

    public String doStep(String input) {
        char lastDigit = input.charAt(0);
        int digitCount = 1;

        StringBuilder result = new StringBuilder(input.length() * 2);

        for (char digit : input.substring(1).toCharArray()) {
            if (lastDigit == digit) {
                digitCount++;
            } else {
                result.append(digitCount)//
                        .append(lastDigit);
                digitCount = 1;
                lastDigit = digit;
            }
        }

        result.append(digitCount)//
                .append(lastDigit);

        return result.toString();

    }

}
