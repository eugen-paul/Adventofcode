package net.eugenpaul.adventofcode.y2016.day24;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day24 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day24.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day24.class.getName());

    private int shortestRouteTemp;

    @Getter
    private int shortestRoute;

    @Getter
    private int shortestRoute2;

    private DuctLayout layout;

    private boolean returnToStart;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2016/day24/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "shortestRoute " + getShortestRoute());
        logger.log(Level.INFO, () -> "shortestRoute2 " + getShortestRoute2());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        layout = DuctLayout.fromStringList(eventData);

        int maxNumber = 0;
        while (layout.getNumberPosition(maxNumber) != null) {
            maxNumber++;
        }

        // Puzzle 1
        shortestRouteTemp = Integer.MAX_VALUE;
        returnToStart = false;
        List<Integer> numbers = new LinkedList<>();
        for (int i = 1; i < maxNumber; i++) {
            numbers.add(i);
        }

        permutationsRecursive(numbers.size(), numbers.toArray(new Integer[0]));

        shortestRoute = shortestRouteTemp;

        // Puzzle 2
        shortestRouteTemp = Integer.MAX_VALUE;
        returnToStart = true;
        numbers = new LinkedList<>();
        for (int i = 1; i < maxNumber; i++) {
            numbers.add(i);
        }

        permutationsRecursive(numbers.size(), numbers.toArray(new Integer[0]));
        shortestRoute2 = shortestRouteTemp;

        return true;
    }

    /**
     * Permutation with Heap's algorithm.
     * 
     * @param n        number of elements.
     * @param elements input array (will be modified).
     */
    private void permutationsRecursive(int n, Integer[] elements) {
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

    private void swap(Integer[] input, int a, int b) {
        Integer tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private void computeDistanceOfPermutation(Integer[] input) {
        var startPos = layout.getNumberPosition(0);
        Dijkstra pathfinding;

        int route = 0;
        for (Integer c : input) {
            pathfinding = new Dijkstra(layout);
            var endPos = layout.getNumberPosition(c);
            route += pathfinding.getSteps(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());

            startPos = endPos;
        }

        if (returnToStart) {
            pathfinding = new Dijkstra(layout);
            route += pathfinding.getSteps(startPos.getX(), startPos.getY(), layout.getNumberPosition(0).getX(), layout.getNumberPosition(0).getY());
        }

        shortestRouteTemp = Math.min(shortestRouteTemp, route);
    }

}
