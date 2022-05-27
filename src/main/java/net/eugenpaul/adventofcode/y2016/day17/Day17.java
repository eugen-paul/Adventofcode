package net.eugenpaul.adventofcode.y2016.day17;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day17 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day17.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day17.class.getName());

    @Getter
    private String shortestPath;

    @Getter
    private int longestPathLength;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2016/day17/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "shortestPath : " + getShortestPath());
        logger.log(Level.INFO, () -> "longestPathLength : " + getLongestPathLength());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        BuildingArea area = new BuildingArea(4, 4);
        Dijkstra pathFinding = new Dijkstra();
        shortestPath = pathFinding.getSteps(area, eventData, 0, 0, 3, 3);

        pathFinding = new Dijkstra();
        longestPathLength = pathFinding.getLongestPathLength(area, eventData, 0, 0, 3, 3);

        return true;
    }

}
