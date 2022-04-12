package net.eugenpaul.adventofcode.y2015.day4;

import java.util.Arrays;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Crypto;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

    private static final byte[] ZERO_ARRAY = new byte[] { 0, 0, 0 };

    @Getter
    private Long lowestFiveZeroNumber = null;
    @Getter
    private Long lowestSixZeroNumber = null;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2015/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        lowestFiveZeroNumber = null;
        lowestSixZeroNumber = null;

        long number = 0;
        do {
            number++;
            byte[] hash = Crypto.doMd5(eventData + number);

            if (Arrays.compare(ZERO_ARRAY, 0, 3, hash, 0, 3) == 0) {
                lowestSixZeroNumber = number;
            }

            hash[2] = (byte) (hash[2] & 0xF0);
            if (null == lowestFiveZeroNumber && Arrays.compare(ZERO_ARRAY, 0, 3, hash, 0, 3) == 0) {
                lowestFiveZeroNumber = number;
            }
        } while (null == lowestFiveZeroNumber || null == lowestSixZeroNumber);

        logger.log(Level.INFO, () -> "lowestFiveZeroNumber  " + lowestFiveZeroNumber);
        logger.log(Level.INFO, () -> "lowestSixZeroNumber  " + lowestSixZeroNumber);
        return true;
    }
}
