package net.eugenpaul.adventofcode.y2015.day25;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day25 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day25.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day25.class.getName());

    private long code;

    public long getCode() {
        return code;
    }

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2015/day25/puzzle1.txt");
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
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "code : " + getCode());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        InfinitePaper paper = new InfinitePaper(20151125L, 252533L, 33554393L);
        String[] elements = eventData.split(" ");
        long posX = Long.parseLong(elements[16].substring(0, elements[16].length() - 1));
        long posY = Long.parseLong(elements[18].substring(0, elements[18].length() - 1));

        code = paper.getElement(posX, posY);

        return true;
    }
}
