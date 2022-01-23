package net.eugenpaul.adventofcode.y2016.day5;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day5 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day5.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day5.class.getName());
    private static final byte[] ZERO_ARRAY = new byte[] { 0, 0, 0 };

    @Getter
    private String password = null;

    @Getter
    private String secondPassword = null;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        try {
            puzzle.doPuzzleFromFile("y2016/day5/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "password  " + getPassword());
        logger.log(Level.INFO, () -> "secondPassword  " + getSecondPassword());

        return true;
    }

    private boolean doEvent(String eventData) throws NoSuchAlgorithmException {
        if (null == eventData) {
            return false;
        }

        StringBuilder passBuilder = new StringBuilder();
        TreeMap<Integer, String> secondPass = new TreeMap<>();

        long number = 0;
        do {
            number++;
            byte[] hash = doMd5(eventData, number);

            int sixthCharacter = (hash[2] & 0x0F);
            int sevensthCharacter = ((hash[3] & 0xF0) >> 4);

            hash[2] = (byte) (hash[2] & 0xF0);
            if (Arrays.compare(ZERO_ARRAY, 0, 3, hash, 0, 3) == 0) {
                if (passBuilder.length() < 8) {
                    passBuilder.append(String.format("%x", sixthCharacter));
                }
                if (secondPass.size() < 8 && sixthCharacter < 8) {
                    secondPass.putIfAbsent(sixthCharacter, String.format("%x", sevensthCharacter));
                }
            }
        } while (passBuilder.length() < 8 || secondPass.size() < 8);

        password = passBuilder.toString();

        StringBuilder secondPassBuilder = new StringBuilder();
        for (Entry<Integer, String> entry : secondPass.entrySet()) {
            secondPassBuilder.append(entry.getValue());
        }
        secondPassword = secondPassBuilder.toString();

        return true;
    }

    private byte[] doMd5(String secret, Long number) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((secret + number).getBytes());
        return md.digest();
    }
}
