package net.eugenpaul.adventofcode.y2019.day16;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.HashStorage;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day16 extends SolutionTemplate {

    @Getter
    private String output;
    @Getter
    private String realOutput;

    private int[] basePattern = { 0, 1, 0, -1 };

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2019/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        output = doPuzzle(eventData);

        // int rep = 2;
        // StringBuilder realInput = new StringBuilder(rep * eventData.length());
        // for (int i = 0; i < rep; i++) {
        //     realInput.append(eventData);
        // }

        // String realOutputFFT = doPuzzle(realInput.toString());

        logger.log(Level.INFO, () -> "output     : " + getOutput());
        logger.log(Level.INFO, () -> "realOutput : " + getRealOutput());

        return true;
    }

    private String doPuzzle(String eventData) {
        long[] digits = StringConverter.digitsToLongArray(eventData);
        // HashStorage hs = new HashStorage();
        // hs.add(digitsToString(digits), 0L);

        for (int i = 0; i < 100; i++) {
            long[] outputData = new long[digits.length];
            for (int j = 0; j < digits.length; j++) {
                int[] mult = getPattern(j + 1, digits.length);
                outputData[j] = getDigit(digits, mult);
            }
            // if (hs.add(digitsToString(outputData), (long) i)) {
            // System.out.println("FOUND");
            // }
            digits = outputData;
            // System.out.println("phase: " + i);
            // System.out.println(digitsToString(digits));
        }

        StringBuilder outputString = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            outputString.append(digits[i]);
        }

        // System.out.println(digitsToString(digits));

        return outputString.toString();
    }

    private String digitsToString(long[] digits) {
        StringBuilder outputString = new StringBuilder();
        for (long j : digits) {
            outputString.append(j);
        }
        return outputString.toString();
    }

    private int getDigit(long[] digits, int[] mult) {
        long result = 0;
        for (int i = 0; i < digits.length; i++) {
            result += digits[i] * mult[i];
        }
        return (int) (Math.abs(result) % 10L);
    }

    private int[] getPattern(int outputPosition, int len) {
        int[] responsePattern = new int[len];
        int basePatternPos = 0;
        int rep = 1;

        for (int i = 0; i < len; i++) {
            if (rep == outputPosition) {
                rep = 0;
                basePatternPos++;
                if (basePatternPos == basePattern.length) {
                    basePatternPos = 0;
                }
            }

            responsePattern[i] = basePattern[basePatternPos];
            rep++;
        }

        return responsePattern;
    }

}
