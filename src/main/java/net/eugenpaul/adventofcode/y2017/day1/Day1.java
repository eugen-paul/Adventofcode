package net.eugenpaul.adventofcode.y2017.day1;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day1 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day1.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day1.class.getName());

    @Getter
    private long sum;
    @Getter
    private long sum2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2017/day1/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(String eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "sum: " + getSum());
        logger.log(Level.INFO, () -> "sum2: " + getSum2());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        long[] digits = eventData.chars().mapToLong(v -> v - '0').toArray();

        doPuzzle1(digits);
        doPuzzle2(digits);

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
