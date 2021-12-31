package net.eugenpaul.adventofcode.y2015.day13;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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

    private FullPlan personList;
    private int maxHappiness;
    private int maxHappinessWithMe;

    public int getMaxHappiness() {
        return maxHappiness;
    }

    public int getMaxHappinessWithMe() {
        return maxHappinessWithMe;
    }

    public Day13() {
        personList = new FullPlan();
    }

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2015/day13/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "MaxHappiness: " + getMaxHappiness());
        logger.log(Level.INFO, () -> "MaxHappiness with me: " + getMaxHappinessWithMe());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        List<Integer> happinessList = new ArrayList<>();

        for (String data : eventData) {
            String[] parts = data.split(" ");
            String name = parts[0];
            String neighbor = parts[10].substring(0, parts[10].length() - 1);
            int relation = parts[2].equals("gain") ? Integer.parseInt(parts[3]) : Integer.parseInt(parts[3]) * (-1);
            personList.addRelation(name, neighbor, relation);
        }

        List<String> names = personList.getPersonList();
        permutationsRecursive(names.size(), names.toArray(new String[0]), personList, happinessList);
        Collections.sort(happinessList);
        maxHappiness = happinessList.get(happinessList.size() - 1);

        // add me ("I") to list and compute the maxHappinessWithMe
        for (String name : names) {
            personList.addRelation(name, "I", 0);
            personList.addRelation("I", name, 0);
        }
        names = personList.getPersonList();
        happinessList.clear();
        permutationsRecursive(names.size(), names.toArray(new String[0]), personList, happinessList);
        Collections.sort(happinessList);
        maxHappinessWithMe = happinessList.get(happinessList.size() - 1);

        return true;
    }

    /**
     * Permutation with Heap's algorithm.
     * 
     * @param n        number of elements.
     * @param elements input array (will be modified).
     */
    private void permutationsRecursive(int n, String[] elements, FullPlan plan, List<Integer> outputHappiness) {
        if (n == 1) {
            computeDistanceOfPermutation(elements, plan, outputHappiness);
        } else {
            for (int i = 0; i < n - 1; i++) {
                permutationsRecursive(n - 1, elements, plan, outputHappiness);
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            permutationsRecursive(n - 1, elements, plan, outputHappiness);
        }
    }

    private void swap(String[] input, int a, int b) {
        String tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private void computeDistanceOfPermutation(String[] input, FullPlan plan, List<Integer> outputHappiness) {
        outputHappiness.add(plan.getTotalHappiness(input));
    }

}
