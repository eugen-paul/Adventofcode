package net.adventofcode.y2015.day9;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.adventofcode.helper.FileReaderHelper;

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

    private FullMap fullMap;
    private List<Integer> distances;

    public int getShortestRoteLength() {
        return distances.get(0);
    }

    public int getLongestRoteLength() {
        return distances.get(distances.size() - 1);
    }

    public Day9() {
        fullMap = new FullMap();
    }

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2015/day9/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "shortestRoteLength: " + getShortestRoteLength());
        logger.log(Level.INFO, () -> "longestRoteLength: " + getLongestRoteLength());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        distances = new ArrayList<>();

        for (String data : eventData) {
            fullMap.addRoute(Route.fromString(data));
        }

        List<String> locations = fullMap.getLocationList();

        permutationsRecursive(locations.size(), locations.toArray(new String[0]));

        Collections.sort(distances);

        return true;
    }

    /**
     * Permutation with Heap's algorithm.
     * 
     * @param n        number of elements.
     * @param elements input array (will be modified).
     */
    private void permutationsRecursive(int n, String[] elements) {
        if (n == 1) {
            computeDistanceOfPermutation(elements);
        } else {
            for (int i = 0; i < n - 1; i++) {
                permutationsRecursive(n - 1, elements);
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            permutationsRecursive(n - 1, elements);
        }
    }

    private void swap(String[] input, int a, int b) {
        String tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private void computeDistanceOfPermutation(String[] input) {
        distances.add(fullMap.getDistance(input));
    }

}
