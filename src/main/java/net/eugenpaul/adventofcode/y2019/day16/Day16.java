package net.eugenpaul.adventofcode.y2019.day16;

import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day16 extends SolutionTemplate {

    @Getter
    private String output;
    @Getter
    private String realOutput;

    @Setter
    private boolean doStep2 = true;

    private int[] basePattern = { 0, 1, 0, -1 };

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2019/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        output = doPuzzle1(eventData);

        if (doStep2) {
            realOutput = doPuzzle2(eventData);
        }

        logger.log(Level.INFO, () -> "output     : " + getOutput());
        logger.log(Level.INFO, () -> "realOutput : " + getRealOutput());

        return true;
    }

    private String doPuzzle1(String eventData) {
        long[] digits = StringConverter.digitsToLongArray(eventData);

        for (int i = 0; i < 100; i++) {
            long[] outputData = new long[digits.length];
            for (int j = 0; j < digits.length; j++) {
                int[] mult = getPattern(j + 1, digits.length);
                outputData[j] = getDigit(digits, mult);
            }
            digits = outputData;
        }

        StringBuilder outputString = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            outputString.append(digits[i]);
        }

        return outputString.toString();
    }

    /**
     * The digits from n/2, where n is equal to the total length, are always the sum of all subsequent digits. It is so because of the repetition of the digit 1
     * in basic pattern. Since the offset is after n/2, you do not have to calculate the whole output but only the last digits from the offset.
     * 
     * <code>
     * Input signal: 12345678
     * 1*1  + 2*0  + 3*-1 + 4*0  + 5*1  + 6*0  + 7*-1 + 8*0  = 4
     * 1*0  + 2*1  + 3*1  + 4*0  + 5*0  + 6*-1 + 7*-1 + 8*0  = 8
     * 1*0  + 2*0  + 3*1  + 4*1  + 5*1  + 6*0  + 7*0  + 8*0  = 2
     * 1*0  + 2*0  + 3*0  + 4*1  + 5*1  + 6*1  + 7*1  + 8*0  = 2
     * 1*0  + 2*0  + 3*0  + 4*0  + 5*1  + 6*1  + 7*1  + 8*1  = 6 <- sum of {n-3, n-2, n-1, n}
     * 1*0  + 2*0  + 3*0  + 4*0  + 5*0  + 6*1  + 7*1  + 8*1  = 1 <- sum of {n-2, n-1, n}
     * 1*0  + 2*0  + 3*0  + 4*0  + 5*0  + 6*0  + 7*1  + 8*1  = 5 <- sum of {n-1, n}
     * 1*0  + 2*0  + 3*0  + 4*0  + 5*0  + 6*0  + 7*0  + 8*1  = 8 <- sum of {n}
     * </code>
     * 
     * @param eventData
     * @return
     */
    private String doPuzzle2(String eventData) {
        long[] digitspart = StringConverter.digitsToLongArray(eventData);
        long[] digits = new long[digitspart.length * 10_000];

        for (int i = 0; i < 10_000; i++) {
            System.arraycopy(digitspart, 0, digits, digitspart.length * i, digitspart.length);
        }

        int offset = Integer.parseInt(eventData.substring(0, 7));

        for (int i = 0; i < 100; i++) {
            long[] outputData = new long[digits.length];
            long value = 0L;
            for (int pos = digits.length - 1; pos >= offset; pos--) {
                value += digits[pos];
                value = (Math.abs(value) % 10L);
                outputData[pos] = value;
            }
            digits = outputData;
        }

        StringBuilder outputString = new StringBuilder();
        for (int i = offset; i < offset + 8; i++) {
            outputString.append(digits[i]);
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
