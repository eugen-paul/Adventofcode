package net.eugenpaul.adventofcode.y2015.day16;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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

    private int sueNumber;
    private int sueNumberRanges;

    public long getSueNumber() {
        return sueNumber;
    }

    public long getSueNumberRanges() {
        return sueNumberRanges;
    }

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2015/day16/puzzle1.txt", "y2015/day16/puzzle1_mfcsam.txt");
    }

    public boolean doPuzzleFromFile(String filename, String filenameMfcsam) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        List<String> mfcsamData = FileReaderHelper.readListStringFromFile(filenameMfcsam);
        if (null == mfcsamData) {
            return false;
        }

        return doPuzzleFromData(eventData, mfcsamData);
    }

    public boolean doPuzzleFromData(List<String> eventData, List<String> mfcsamData) {
        if (!doEvent(eventData, mfcsamData)) {
            logger.log(Level.INFO, () -> "Aunt not Found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "sueNumber: " + getSueNumber());
        logger.log(Level.INFO, () -> "sueNumberRanges: " + getSueNumberRanges());

        return true;
    }

    private boolean doEvent(List<String> eventData, List<String> mfcsamData) {
        if (null == eventData) {
            return false;
        }

        List<Aunt> aunts = new LinkedList<>();
        for (String data : eventData) {
            aunts.add(Aunt.fromString(data));
        }

        MfcsamData mfcsam = MfcsamData.fromList(mfcsamData);

        sueNumber = -1;
        for (Aunt aunt : aunts) {
            if (mfcsam.chechAunt(aunt)) {
                sueNumber = aunt.getNumber();
                break;
            }
        }

        sueNumberRanges = -1;
        for (Aunt aunt : aunts) {
            if (mfcsam.chechAuntRanges(aunt)) {
                sueNumberRanges = aunt.getNumber();
                break;
            }
        }

        return sueNumber != -1 && sueNumberRanges != -1;
    }
}
