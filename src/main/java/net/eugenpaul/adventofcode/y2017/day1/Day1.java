package net.eugenpaul.adventofcode.y2017.day1;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day1 extends SolutionTemplate {

    @Getter
    private long sum;
    @Getter
    private long sum2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2017/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        long[] digits = StringConverter.digitsToLongArray(eventData);

        doPuzzle1(digits);
        doPuzzle2(digits);

        logger.log(Level.INFO, () -> "sum: " + getSum());
        logger.log(Level.INFO, () -> "sum2: " + getSum2());

        return true;
    }

    private void doPuzzle1(long[] digits) {
        sum = 0;
        Long last = null;

        for (long l : digits) {
            if (last != null && last.longValue() == l) {
                sum += l;
            }
            last = l;
        }
        if (last != null && last.longValue() == digits[0]) {
            sum += last;
        }
    }

    private void doPuzzle2(long[] digits) {
        sum2 = 0;
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] == digits[(i + (digits.length / 2)) % digits.length]) {
                sum2 += digits[i];
            }
        }
    }

}
