package net.eugenpaul.adventofcode.y2015.day17;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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

    private int combinations;
    private int minContainerCombinations;
    private int minContainers;

    public long getCombinations() {
        return combinations;
    }

    public long getMinContainerCombinations() {
        return minContainerCombinations;
    }

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2015/day17/puzzle1.txt", "y2015/day17/puzzle1_liters.txt");
    }

    public boolean doPuzzleFromFile(String filename, String filenameLiters) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        String liters = FileReaderHelper.readStringFromFile(filenameLiters);
        if (null == liters) {
            return false;
        }

        return doPuzzleFromData(eventData, liters);
    }

    public boolean doPuzzleFromData(List<String> eventData, String liters) {
        if (!doEvent(eventData, liters)) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "combinations: " + getCombinations());
        logger.log(Level.INFO, () -> "minContainerCombinations: " + getMinContainerCombinations());

        return true;
    }

    private boolean doEvent(List<String> eventData, String liters) {
        if (null == eventData) {
            return false;
        }

        combinations = 0;
        minContainerCombinations = 0;
        minContainers = Integer.MAX_VALUE;

        int[] containers = eventData.stream()//
                .map(Integer::parseInt)//
                .sorted((a, b) -> b.compareTo(a)) //
                .mapToInt(i -> i)//
                .toArray();

        position(containers, 0, Integer.parseInt(liters), 0);

        return true;
    }

    private void position(int[] containers, int pos, int liters, int containerCount) {
        if (liters == 0) {
            combinations++;
            if (minContainers == containerCount) {
                minContainerCombinations++;
            } else if (minContainers > containerCount) {
                minContainers = containerCount;
                minContainerCombinations = 1;
            }
            return;
        }

        if (liters < 0) {
            return;
        }

        for (int i = pos; i < containers.length; i++) {
            position(containers, i + 1, liters - containers[i], containerCount + 1);
        }
    }
}
