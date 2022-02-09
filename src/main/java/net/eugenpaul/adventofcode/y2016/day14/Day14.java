package net.eugenpaul.adventofcode.y2016.day14;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.helper.HexConverter;

public class Day14 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day14.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day14.class.getName());

    @Getter
    private long index;
    @Getter
    private long index2;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2016/day14/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "index : " + getIndex());
        logger.log(Level.INFO, () -> "index2 : " + getIndex2());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        index = puzzle(eventData, 1);
        index2 = puzzle(eventData, 2017);

        return true;
    }

    private long puzzle(String eventData, int iterations) {
        TreeMap<Long, Character> possibleKeys = new TreeMap<>();
        TreeMap<Long, Character> foundKeys = new TreeMap<>();

        Long seed = 0L;
        while (isNotDone(possibleKeys, foundKeys)) {
            String hash = doMd5(eventData, seed, iterations);

            var iterator = possibleKeys.entrySet().iterator();
            while (iterator.hasNext()) {
                var set = iterator.next();
                if (set.getKey() <= seed - 1000) {
                    // No sequence of 5 same characters was found within the 1000 hashes => remove key
                    iterator.remove();
                } else if (checkFiveInRow(hash, set.getValue())) {
                    foundKeys.put(set.getKey(), set.getValue());
                    iterator.remove();
                }
            }

            Character tripleChar = getFirstTriple(hash);
            if (tripleChar != null) {
                possibleKeys.put(seed, tripleChar);
            }
            seed++;
        }

        return get64Key(foundKeys);
    }

    private Long get64Key(TreeMap<Long, Character> keys) {
        Long[] a = keys.keySet().toArray(new Long[0]);
        return a[63];
    }

    private boolean isNotDone(TreeMap<Long, Character> hashNumberToCheck, TreeMap<Long, Character> keys) {
        if (keys.size() < 64) {
            return true;
        }

        // We are not finished until the first 64 keys have been checked.
        // It could be that later keys are found one earlier than an earlier key.
        Long key64 = get64Key(keys);
        return key64 > hashNumberToCheck.firstKey();
    }

    private String doMd5(String secret, Long number, int iterations) {
        String data = secret + number;
        for (int i = 0; i < iterations; i++) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException("Cann't init MD5");
            }
            md.update(data.getBytes());
            data = HexConverter.bytesToHexString(md.digest()).toLowerCase();
        }
        return data;
    }

    private Character getFirstTriple(String hash) {
        for (int i = 0; i < hash.length() - 2; i++) {
            if (hash.charAt(i) == hash.charAt(i + 1)//
                    && hash.charAt(i) == hash.charAt(i + 2)//
            ) {
                return hash.charAt(i);
            }
        }

        return null;
    }

    private boolean checkFiveInRow(String hash, Character c) {
        for (int i = 0; i < hash.length() - 4; i++) {
            if (c == hash.charAt(i)//
                    && c == hash.charAt(i + 1)//
                    && c == hash.charAt(i + 2)//
                    && c == hash.charAt(i + 3)//
                    && c == hash.charAt(i + 4)//
            ) {
                return true;
            }
        }

        return false;
    }

}
