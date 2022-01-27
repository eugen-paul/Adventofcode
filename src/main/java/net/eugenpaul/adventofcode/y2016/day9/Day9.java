package net.eugenpaul.adventofcode.y2016.day9;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day9 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day9.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day9.class.getName());

    @Getter
    private long textLength;

    @Getter
    private long textLengthV2;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2016/day9/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "textLength: " + getTextLength());
        logger.log(Level.INFO, () -> "textLengthV2: " + getTextLengthV2());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        textLength = computeFieldLengthV2(eventData, new AtomicInteger(0), new Marker(eventData.length(), 1, 0), 1);

        textLengthV2 = computeFieldLengthV2(eventData, new AtomicInteger(0), new Marker(eventData.length(), 1, 0), Integer.MAX_VALUE);

        return true;
    }

    private long computeFieldLengthV2(String text, AtomicInteger position, Marker marker, int depthRest) {
        int endOfField = position.get() + marker.charCount;

        long count = 0;
        while (position.get() < endOfField) {
            char c = text.charAt(position.get());
            position.incrementAndGet();
            if (c == '(' && depthRest > 0) {
                Marker in = Marker.fromString(text, position.get());
                position.addAndGet(in.markerLen + 1);
                count += computeFieldLengthV2(text, position, in, depthRest - 1);
            } else {
                if (c != ' ') {
                    count++;
                }
            }
        }
        return marker.repetition * count;
    }

}
