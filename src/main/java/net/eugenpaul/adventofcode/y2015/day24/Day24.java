package net.eugenpaul.adventofcode.y2015.day24;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
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

        int groupWeight = eventData.stream().reduce(0, Integer::sum) / groupCount;
        TreeMap<Integer, List<List<Integer>>> allGroups = findGroupsWithWeight(new LinkedList<>(eventData), groupWeight);

        List<List<Integer>> asList = treeMapToList(allGroups);

        Integer minGroupLen = null;
        quantumEntanglement = Integer.MAX_VALUE;

        for (List<Integer> list : asList) {
            if (isOk(asList, list, groupCount)) {
                long qe = getQuantumEntanglement(list);
                if (minGroupLen == null) {
                    quantumEntanglement = qe;
                    minGroupLen = list.size();
                } else if (minGroupLen < list.size()) {
                    return true;
                } else if (qe < quantumEntanglement) {
                    quantumEntanglement = qe;
                }
            }
        }

        return true;
    }

    /**
     * The group is ok if the remaining groups can be built from the remaining packages.
     * 
     * @param possibleGroups - The list of all remaining possible groups
     * @param checkGroup     - Group currently being checked
     * @param count          - number of groups that must also be formed.
     * @return true - the group is OK.
     */
    private boolean isOk(List<List<Integer>> possibleGroups, List<Integer> checkGroup, int count) {
        if (possibleGroups.size() == 1 && isEqual(possibleGroups.get(0), checkGroup) && count == 1) {
            return true;
        }

        possibleGroups = getPossibleGroups(possibleGroups, checkGroup);

        if (possibleGroups.isEmpty()) {
            return false;
        }

        for (List<Integer> mb : possibleGroups) {
            if (isOk(possibleGroups, mb, count - 1)) {
                return true;
            }
        }

        return false;

    }

    /**
     * 
     * @param a
     * @param b
     * @return true if List contains equal Elements
     */
    private boolean isEqual(List<Integer> a, List<Integer> b) {
        for (Integer integer : b) {
            if (!a.contains(integer)) {
                return false;
            }
        }
        for (Integer integer : a) {
            if (!b.contains(integer)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Delete all groups from the list that cannot occur and response the list.
     * 
     * @param groups
     * @param filterGroup
     * @return
     */
    private List<List<Integer>> getPossibleGroups(List<List<Integer>> groups, List<Integer> filterGroup) {
        List<List<Integer>> response = new LinkedList<>();
        for (List<Integer> elem : groups) {
            boolean ok = true;
            for (Integer data : elem) {
                if (filterGroup.contains(data)) {
                    ok = false;
                }
            }
            if (ok) {
                response.add(elem);
            }
        }
        return response;
    }

    /**
     * Convert all groups from TreeMap to List. All groups will be sorted by packagesize.
     * 
     * @param allGroups
     * @return
     */
    private List<List<Integer>> treeMapToList(TreeMap<Integer, List<List<Integer>>> allGroups) {
        List<List<Integer>> response = new LinkedList<>();
        for (Entry<Integer, List<List<Integer>>> list : allGroups.entrySet()) {
            response.addAll(list.getValue());
        }
        return response;
    }

    /**
     * Finds all possible groups with a given weight.
     * 
     * @param avaiblePackages
     * @param weight
     * @return Sorted Map of groups
     */
    private TreeMap<Integer, List<List<Integer>>> findGroupsWithWeight(LinkedList<Integer> avaiblePackages, int weight) {
        TreeMap<Integer, List<List<Integer>>> response = new TreeMap<>();
        LinkedList<Integer> group = new LinkedList<>();

        groupSearch(avaiblePackages, group, response, weight);

        return response;
    }

    private void groupSearch(LinkedList<Integer> avaiblePackages, LinkedList<Integer> group, Map<Integer, List<List<Integer>>> response, int sum) {
        Integer testPacket = avaiblePackages.pollFirst();
        while (testPacket != null) {
            group.addLast(testPacket);

            int currentGroupSum = group.stream().reduce(0, Integer::sum);

            if (sum == currentGroupSum) {
                addGroupToTree(group, response);
            } else if (sum < currentGroupSum) {
                return;
            }

            groupSearch(new LinkedList<>(avaiblePackages), new LinkedList<>(group), response, sum);

            group.removeLast();

            testPacket = avaiblePackages.pollFirst();
        }
    }

    private void addGroupToTree(LinkedList<Integer> group, Map<Integer, List<List<Integer>>> response) {
        response.compute(group.size(), (k, v) -> {
            if (v == null) {
                List<List<Integer>> value = new LinkedList<>();
                value.add(new LinkedList<>(group));
                return value;
            }
            v.add(new LinkedList<>(group));
            return v;
        });
    }

    private Long getQuantumEntanglement(List<Integer> group) {
        return group.stream().mapToLong(v -> v).reduce(1L, (a, b) -> a * b);
    }
}
