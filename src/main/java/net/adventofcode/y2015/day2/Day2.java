package net.adventofcode.y2015.day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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

    private Long totalSquare = null;
    private Long totalRibbon = null;

    public Long getTotalSquare() {
        return this.totalSquare;
    }

    public Long getTotalRibbon() {
        return this.totalRibbon;
    }

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2015/day2/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<Square> eventData = readDataFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<Square> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "Total square feet of wrapping paper " + totalSquare);
        logger.log(Level.INFO, () -> "Total feet of ribbon  " + totalRibbon);

        return true;
    }

    private List<Square> readDataFromFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        if (!file.canRead() || !file.isFile()) {
            return Collections.emptyList();
        }

        List<Square> responseData = new LinkedList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                Square data = Square.fromString(zeile);
                if (null == data) {
                    logger.log(Level.WARNING, () -> "Error: cannt read Squaredata");
                    return Collections.emptyList();
                }
                responseData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData;
    }

    private boolean doEvent(List<Square> eventData) {
        if (null == eventData) {
            return false;
        }
        long responseTotalSquare = 0;
        long responseTotalRibbon = 0;
        for (Square box : eventData) {
            responseTotalSquare += ElvesHelper.computeSurfaceArea(box);
            responseTotalRibbon += ElvesHelper.computeRibbon(box);
        }

        totalSquare = responseTotalSquare;
        totalRibbon = responseTotalRibbon;

        return true;
    }

}
