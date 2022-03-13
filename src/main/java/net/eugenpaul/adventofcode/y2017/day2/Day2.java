package net.eugenpaul.adventofcode.y2017.day2;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day2 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day2.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day2.class.getName());

    @Getter
    private long checksum;
    @Getter
    private long checksum2;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2017/day2/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "checksum : " + getChecksum());
        logger.log(Level.INFO, () -> "checksum2 : " + getChecksum2());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        checksum = 0;
        checksum2 = 0;
        for (String string : eventData) {
            List<Integer> elements = Stream.of(string.split("[\t ]"))//
                    .mapToInt(Integer::parseInt)//
                    .boxed()//
                    .sorted() //
                    .collect(Collectors.toList());

            checksum += checksumPuzzle1(elements);
            checksum2 += checksumPuzzle2(elements);
        }

        return true;
    }

    private int checksumPuzzle1(List<Integer> data) {
        return data.get(data.size() - 1) - data.get(0);
    }

    private int checksumPuzzle2(List<Integer> data) {

        for (int i = 0; i < data.size() - 1; i++) {
            int a = data.get(i);
            for (int k = i + 1; k < data.size(); k++) {
                int b = data.get(k);
                if (b % a == 0) {
                    return b / a;
                }
            }
        }

        return 0;
    }

}
