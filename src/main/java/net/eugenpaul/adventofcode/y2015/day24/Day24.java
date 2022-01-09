package net.eugenpaul.adventofcode.y2015.day24;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    private int minGroupLen;
    private long quantumEntanglement;

    public long getQuantumEntanglement() {
        return quantumEntanglement;
    }

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2015/day24/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData.stream().map(Integer::parseInt).collect(Collectors.toList()), 3)) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "min quantumEntanglement by 3 groups : " + getQuantumEntanglement());

        if (!doEvent(eventData.stream().map(Integer::parseInt).collect(Collectors.toList()), 4)) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "min quantumEntanglement by 4 groups : " + getQuantumEntanglement());

        return true;
    }

    public boolean doEvent(List<Integer> eventData, int groupCount) {
        if (null == eventData) {
            return false;
        }

        doPuzzle(new LinkedList<>(eventData), groupCount);

        return true;
    }

    private void doPuzzle(LinkedList<Integer> eventData, int groupCount) {
        minGroupLen = Integer.MAX_VALUE;
        quantumEntanglement = Integer.MAX_VALUE;

        int totalSum = eventData.stream().reduce(0, Integer::sum);

        // Storage for found groups, in case we need it.
        // Groups can appear twice in the memory.
        LinkedList<LinkedList<Integer>> allGroups = new LinkedList<>();

        getGroup(//
                new LinkedList<>(eventData), //
                new LinkedList<>(), //
                new LinkedList<>(), //
                totalSum / groupCount, //
                1, //
                groupCount, //
                allGroups//
        );
    }

    /**
     * The return value depends on the group.<br>
     * Group 1 return always false to keep searching.<br>
     * All other groups return true if the search are successful.
     * 
     * The Solution workd for me but i don't like it.
     */
    private boolean getGroup(//
            LinkedList<Integer> avaiblePackages, //
            LinkedList<Integer> restPackages, //
            LinkedList<Integer> group, //
            int groupWeight, //
            int currentGroupNumber, //
            int maxGropus, //
            LinkedList<LinkedList<Integer>> allGroups //
    ) {
        int currentGroupSum = group.stream().reduce(0, Integer::sum);

        if (groupWeight < currentGroupSum) {
            // Current group a to large => try next
            return false;
        }

        if (groupWeight == currentGroupSum) {
            // next-to-last group found => last group must be a right group => save both
            if (currentGroupNumber == maxGropus - 1) {
                allGroups.add(group);
                countQuantumEntanglement(group);

                LinkedList<Integer> thirdGroup = new LinkedList<>(avaiblePackages);
                thirdGroup.addAll(restPackages);
                allGroups.add(thirdGroup);

                countQuantumEntanglement(thirdGroup);
                return true;
            }
            LinkedList<Integer> newAvaiblePackages = new LinkedList<>(avaiblePackages);
            newAvaiblePackages.addAll(restPackages);
            if (getGroup(newAvaiblePackages, new LinkedList<>(), new LinkedList<>(), groupWeight, currentGroupNumber + 1, maxGropus, allGroups)) {
                allGroups.add(new LinkedList<>(group));
                countQuantumEntanglement(group);
                // Group 1 return always false to keep searching
                return currentGroupNumber != 1;
            }
            return false;
        }

        Integer testPackage = avaiblePackages.pollFirst();

        while (testPackage != null) {
            group.push(testPackage);

            if (getGroup(new LinkedList<>(avaiblePackages), new LinkedList<>(restPackages), group, groupWeight, currentGroupNumber, maxGropus, allGroups)) {
                return true;
            }

            group.pop();
            restPackages.push(testPackage);
            if (group.isEmpty()) {
                //the current Group must have the element with lowest weight.
                //By removing der last Element stop the search
                return false;
            }
            testPackage = avaiblePackages.pollFirst();
        }

        return false;
    }

    private void countQuantumEntanglement(LinkedList<Integer> group) {
        long qe = group.stream().mapToLong(v -> v).reduce(1L, (a, b) -> a * b);
        if (group.size() < minGroupLen) {
            minGroupLen = group.size();
            quantumEntanglement = qe;
        } else if (group.size() == minGroupLen) {
            quantumEntanglement = (qe < quantumEntanglement) ? qe : quantumEntanglement;
        }
    }
}
