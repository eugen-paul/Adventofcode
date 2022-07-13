package net.eugenpaul.adventofcode.y2020.day10;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day10 extends SolutionTemplate {

    @Getter
    private int solution1;
    @Getter
    private long ways;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2020/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        LinkedList<Integer> dataSorted = eventData.stream()//
                .map(Integer::parseInt)//
                .sorted()//
                .collect(Collectors.toCollection(LinkedList::new));

        Map<Integer, Integer> difCount = new HashMap<>();
        var iterator = dataSorted.iterator();
        int last = 0;

        int oneInRow = 0;
        int maxOneInRow = 0;

        ways = 1;

        while (iterator.hasNext()) {
            int current = iterator.next();
            int dif = current - last;
            difCount.compute(dif, (k, v) -> (v == null) ? 1 : v + 1);
            last = current;

            if (dif == 1) {
                oneInRow++;
            } else {
                maxOneInRow = Math.max(maxOneInRow, oneInRow);
                ways = computeWays(ways, oneInRow);
                oneInRow = 0;
            }
        }

        ways = computeWays(ways, oneInRow);

        // add your device's built-in adapter
        difCount.compute(3, (k, v) -> (v == null) ? 1 : v + 1);

        solution1 = difCount.get(1) * difCount.get(3);

        logger.log(Level.INFO, () -> "solution1  : " + getSolution1());
        logger.log(Level.INFO, () -> "ways  : " + getWays());

        return true;
    }

    private long computeWays(long ways, int oneInRow) {
        if (oneInRow == 2) {
            return ways * 2L;
        } else if (oneInRow == 3) {
            return ways * 4L;
        } else if (oneInRow == 4) {
            return ways * 7L;
        }
        return ways;
    }

}
