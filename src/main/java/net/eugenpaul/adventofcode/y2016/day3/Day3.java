package net.eugenpaul.adventofcode.y2016.day3;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day3 {

    private static final String REGEX = "[ ]*(\\d+)[ ]+(\\d+)[ ]+(\\d+)$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day3.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day3.class.getName());

    @Getter
    private int triangleCount;

    @Getter
    private int triangleVerticallyCount;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2016/day3/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "triangleCount: " + getTriangleCount());
        logger.log(Level.INFO, () -> "triangleVerticallyCount: " + getTriangleVerticallyCount());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        triangleCount = 0;
        for (String data : eventData) {
            if (isTriangle(data)) {
                triangleCount++;
            }
        }

        triangleVerticallyCount = 0;
        LinkedList<String> dataCopy = new LinkedList<>(eventData);
        while (!dataCopy.isEmpty()) {
            triangleVerticallyCount += isTriangle(dataCopy.poll(), dataCopy.poll(), dataCopy.poll());
        }

        return true;
    }

    private boolean isTriangle(String data) {
        Matcher matcher = pattern.matcher(data);
        matcher.find();
        return isTriangle(//
                Integer.parseInt(matcher.group(1)), //
                Integer.parseInt(matcher.group(2)), //
                Integer.parseInt(matcher.group(3)) //
        );
    }

    private int isTriangle(String data1, String data2, String data3) {
        Matcher matcher1 = pattern.matcher(data1);
        matcher1.find();
        Matcher matcher2 = pattern.matcher(data2);
        matcher2.find();
        Matcher matcher3 = pattern.matcher(data3);
        matcher3.find();

        int response = 0;
        if (isTriangle(//
                Integer.parseInt(matcher1.group(1)), //
                Integer.parseInt(matcher2.group(1)), //
                Integer.parseInt(matcher3.group(1)) //
        )) {
            response++;
        }
        if (isTriangle(//
                Integer.parseInt(matcher1.group(2)), //
                Integer.parseInt(matcher2.group(2)), //
                Integer.parseInt(matcher3.group(2)) //
        )) {
            response++;
        }
        if (isTriangle(//
                Integer.parseInt(matcher1.group(3)), //
                Integer.parseInt(matcher2.group(3)), //
                Integer.parseInt(matcher3.group(3)) //
        )) {
            response++;
        }

        return response;
    }

    private boolean isTriangle(int a, int b, int c) {
        return a + b > c//
                && a + c > b//
                && b + c > a;
    }
}
