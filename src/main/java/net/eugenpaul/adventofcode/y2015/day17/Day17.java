package net.eugenpaul.adventofcode.y2015.day17;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day17 extends SolutionTemplate {

    @Getter
    private int combinations;
    @Getter
    private int minContainerCombinations;
    private int minContainers;

    @Setter
    private int litersOfEggnog = 150;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2015/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        combinations = 0;
        minContainerCombinations = 0;
        minContainers = Integer.MAX_VALUE;

        int[] containers = eventData.stream()//
                .map(Integer::parseInt)//
                .sorted((a, b) -> b.compareTo(a)) //
                .mapToInt(i -> i)//
                .toArray();

        position(containers, 0, litersOfEggnog, 0);

        logger.log(Level.INFO, () -> "combinations: " + getCombinations());
        logger.log(Level.INFO, () -> "minContainerCombinations: " + getMinContainerCombinations());
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
