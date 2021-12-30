package net.eugenpaul.adventofcode.y2015.day12;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day12 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day12.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day12.class.getName());

    private Integer sum = null;
    private Integer sumWithoutRed = null;

    public Integer getSum() {
        return sum;
    }

    public Integer getSumWithoutRed() {
        return sumWithoutRed;
    }

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2015/day12/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "sum  " + getSum());
        logger.log(Level.INFO, () -> "sumWithoutRed  " + getSumWithoutRed());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        DocumentObject data = DocumentParser.parseData(eventData);

        sum = getSum(data);
        sumWithoutRed = getSumWithoutRed(data);

        return true;
    }

    private int getSum(DocumentObject data) {
        int response = 0;

        if (null != data.getValueInt()) {
            response = data.getValueInt();
        }

        if (null != data.getArrays()) {
            response += data.getArrays().stream().//
                    collect(Collectors.summingInt(this::getSum));
        }

        if (null != data.getObjects()) {
            response += data.getObjects().stream().//
                    collect(Collectors.summingInt(this::getSum));
        }

        return response;
    }

    private int getSumWithoutRed(DocumentObject data) {
        int response = 0;

        if (null != data.getValueInt()) {
            response = data.getValueInt();
        }

        if (null != data.getArrays()) {
            response += data.getArrays().stream().//
                    collect(Collectors.summingInt(this::getSumWithoutRed));
        }

        if (null != data.getObjects()) {
            boolean withoutRed = data.getObjects().stream()//
                    .noneMatch(v -> v.getValueString() != null && v.getValueString().equals("red"));
            if (withoutRed) {
                response += data.getObjects().stream().//
                        collect(Collectors.summingInt(this::getSumWithoutRed));
            }
        }

        return response;
    }

}
