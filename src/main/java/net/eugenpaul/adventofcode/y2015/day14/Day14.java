package net.eugenpaul.adventofcode.y2015.day14;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day14 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day14.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day14.class.getName());

    private int maxDistance;
    private int winnerPoints;

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getWinnerPoints() {
        return winnerPoints;
    }

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2015/day14/puzzle1_reindeer.txt", "y2015/day14/puzzle1_time.txt");
    }

    public boolean doPuzzleFromFile(String filenameReindeer, String fileNameTime) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filenameReindeer);
        if (null == eventData) {
            return false;
        }
        int time = Integer.parseInt(FileReaderHelper.readStringFromFile(fileNameTime));

        return doPuzzleFromData(eventData, time);
    }

    public boolean doPuzzleFromData(List<String> eventData, int time) {
        if (!doEvent(eventData, time)) {
            return false;
        }

        logger.log(Level.INFO, () -> "maxDistance: " + getMaxDistance());
        logger.log(Level.INFO, () -> "winnerPoints: " + getWinnerPoints());

        return true;
    }

    private boolean doEvent(List<String> eventData, int time) {
        if (null == eventData) {
            return false;
        }

        List<Reindeer> reindeers = new ArrayList<>();

        for (String data : eventData) {
            reindeers.add(Reindeer.fromString(data));
        }

        // Part One Solution
        maxDistance = -1;
        for (Reindeer reindeer : reindeers) {
            int distance = reindeer.computeDistance(time);
            if (maxDistance < distance) {
                maxDistance = distance;
            }
        }

        // Part Two Solution
        RaceData race = new RaceData(reindeers);
        race.doRace(time);

        winnerPoints = race.getWinnerPoints();

        return true;
    }

}
