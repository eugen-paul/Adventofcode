package net.eugenpaul.adventofcode.y2021.day7;

import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day7 extends SolutionTemplate {

    @Getter
    private long fuel;
    @Getter
    private long fuel2;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2021/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        List<Integer> horizontalPositions = StringConverter.toIntegerLinkedList(eventData);

        fuel = getFuelCost(horizontalPositions, (pos, target) -> (long) Math.abs(pos - target));
        fuel2 = getFuelCost(horizontalPositions, (pos, target) -> MathHelper.sumRange(1L, Math.abs(pos - target)));

        logger.log(Level.INFO, () -> "fuel  : " + getFuel());
        logger.log(Level.INFO, () -> "fuel2  : " + getFuel2());

        return true;
    }

    private long getFuelCost(List<Integer> horizontalPositions, BiFunction<Integer, Integer, Long> costFunction) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Integer in : horizontalPositions) {
            min = Math.min(min, in);
            max = Math.max(max, in);
        }

        long best = Integer.MAX_VALUE;

        for (int i = min; i <= max; i++) {
            int current = i;
            long cost = horizontalPositions.stream()//
                    .mapToLong(v -> costFunction.apply(v, current))//
                    .sum();
            if (best > cost) {
                best = cost;
            }
        }

        return best;
    }
}
