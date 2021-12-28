package net.eugenpaul.adventofcode.y2015.day4;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day4 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day4.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day4.class.getName());
    private static final byte[] ZERO_ARRAY = new byte[] { 0, 0, 0 };

    private Long lowestFiveZeroNumber = null;
    private Long lowestSixZeroNumber = null;

    public long getLowestFiveZeroNumber() {
        return lowestFiveZeroNumber;
    }

    public long getLowestSixZeroNumber() {
        return lowestSixZeroNumber;
    }

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        try {
            puzzle.doPuzzleFromFile("y2015/day4/puzzle1.txt");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, "NoSuchAlgorithmException", e);
        }
    }

    public boolean doPuzzleFromFile(String filename) throws NoSuchAlgorithmException {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(String eventData) throws NoSuchAlgorithmException {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "lowestFiveZeroNumber  " + lowestFiveZeroNumber);
        logger.log(Level.INFO, () -> "lowestSixZeroNumber  " + lowestSixZeroNumber);

        return true;
    }

    private boolean doEvent(String eventData) throws NoSuchAlgorithmException {
        if (null == eventData) {
            return false;
        }

        lowestFiveZeroNumber = null;
        lowestSixZeroNumber = null;

        long number = 0;
        do {
            number++;
            byte[] hash = doMd5(eventData, number);

            if (Arrays.compare(ZERO_ARRAY, 0, 3, hash, 0, 3) == 0) {
                lowestSixZeroNumber = number;
            }

            hash[2] = (byte) (hash[2] & 0xF0);
            if (null == lowestFiveZeroNumber && Arrays.compare(ZERO_ARRAY, 0, 3, hash, 0, 3) == 0) {
                lowestFiveZeroNumber = number;
            }
        } while (null == lowestFiveZeroNumber || null == lowestSixZeroNumber);

        return true;
    }

    private byte[] doMd5(String secret, Long number) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((secret + number).getBytes());
        return md.digest();
    }
}
