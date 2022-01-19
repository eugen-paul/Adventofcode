package net.eugenpaul.adventofcode.y2016.day1;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day1 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day1.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day1.class.getName());

    @Getter
    private int distance;
    @Getter
    private Integer distancePuzzle2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2016/day1/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "distance to Easter Bunny HQ: " + getDistance());
        logger.log(Level.INFO, () -> "distance from first location you visit twice to Easter Bunny HQ: " + getDistancePuzzle2());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        String[] turns = eventData.split(",");

        int posX = 0;
        int posY = 0;
        Map<String, Integer> locations = new HashMap<>();
        distancePuzzle2 = null;

        Direction direction = Direction.N;

        for (String turn : turns) {
            Turn t = Turn.fromString(turn.trim());
            int range = Integer.parseInt(turn.trim().substring(1));

            direction = direction.nextDirection(t);

            for (int i = 1; i <= range; i++) {
                switch (direction) {
                case N:
                    posY++;
                    break;
                case S:
                    posY--;
                    break;
                case O:
                    posX++;
                    break;
                case W:
                    posX--;
                    break;
                }
                saveAndCheckLocationPoint(locations, posX, posY);
            }
        }

        distance = Math.abs(posX) + Math.abs(posY);

        return true;
    }

    private void saveAndCheckLocationPoint(Map<String, Integer> locations, int posX, int posY) {
        if (null != distancePuzzle2) {
            return;
        }
        String locationKey = posX + ":" + posY;
        if (locations.containsKey(locationKey)) {
            distancePuzzle2 = Math.abs(posX) + Math.abs(posY);
            return;
        }
        locations.put(locationKey, 1);
    }
}
