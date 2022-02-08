package net.eugenpaul.adventofcode.y2016.day13;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day13 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day13.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day13.class.getName());

    @Getter
    private int steps;
    @Getter
    private int distinctLocations;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2016/day13/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(Integer.parseInt(eventData), 31, 39, 50);
    }

    public boolean doPuzzleFromData(int eventData, int x, int y, int maxSteps) {
        if (!doEvent(eventData, x, y, maxSteps)) {
            return false;
        }

        logger.log(Level.INFO, () -> "steps : " + getSteps());
        logger.log(Level.INFO, () -> "distinctLocations : " + getDistinctLocations());

        return true;
    }

    private boolean doEvent(int eventData, int x, int y, int maxSteps) {
        if (x < 0 || y < 0) {
            return false;
        }

        BuildingArea area = new BuildingArea(eventData);
        Dijkstra pathFinding = new Dijkstra();

        steps = pathFinding.getSteps(area, 1, 1, x, y);

        pathFinding = new Dijkstra();
        distinctLocations = pathFinding.getFieldsCount(area, 1, 1, maxSteps);

        return true;
    }

}
