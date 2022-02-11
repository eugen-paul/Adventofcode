package net.eugenpaul.adventofcode.y2016.day16;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day16 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day16.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day16.class.getName());

    @Getter
    private String checksum;

    @Getter
    private String checksum2;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2016/day16/puzzle1.txt", 272, 35651584);
    }

    public boolean doPuzzleFromFile(String filename, int length, int length2) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData, length, length2);
    }

    public boolean doPuzzleFromData(String eventData, int length, int length2) {
        if (!doEvent(eventData, length, length2)) {
            return false;
        }

        logger.log(Level.INFO, () -> "checksum : " + getChecksum());
        logger.log(Level.INFO, () -> "checksum2 : " + getChecksum2());

        return true;
    }

    private boolean doEvent(String eventData, int length, int length2) {
        if (null == eventData) {
            return false;
        }

        checksum = getChecksum(eventData, length);
        checksum2 = getChecksum(eventData, length2);

        return true;
    }

    private String getChecksum(String eventData, int length) {
        String initial = eventData;
        while (initial.length() < length) {
            initial = DragonCurve.doDragonCurve(initial);
        }

        initial = initial.substring(0, length);

        return Checksum.computeChecksum(initial);
    }

}
