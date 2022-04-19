package net.eugenpaul.adventofcode.y2015.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import net.eugenpaul.adventofcode.helper.Permutation;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day9 extends SolutionTemplate {

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

    @Override
    public boolean doEvent(List<String> eventData) {
        distances = new ArrayList<>();

        for (String data : eventData) {
            fullMap.addRoute(Route.fromString(data));
        }

        List<String> locations = fullMap.getLocationList();

        Permutation.permutationsRecursive(//
                locations.size(), //
                locations.toArray(new String[0]), //
                this::computeDistanceOfPermutation //
        );

        Collections.sort(distances);

        logger.log(Level.INFO, () -> "shortestRoteLength: " + getShortestRoteLength());
        logger.log(Level.INFO, () -> "longestRoteLength: " + getLongestRoteLength());

        return true;
    }

    private void computeDistanceOfPermutation(String[] input) {
        distances.add(fullMap.getDistance(input));
    }

}
