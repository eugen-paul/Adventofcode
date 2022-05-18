package net.eugenpaul.adventofcode.y2019.day1;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private int sum;
    @Getter
    private int sum2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2019/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Integer> intData = eventData.stream()//
                .map(Integer::parseInt)//
                .collect(Collectors.toList());

        sum = intData.stream()//
                .map(v -> v / 3 - 2)//
                .reduce(0, (a, b) -> a + b);

        sum2 = intData.stream()//
                .map(v -> {
                    int fuelNeedTotal = 0;
                    int fuelNeed = v / 3 - 2;
                    while (fuelNeed > 0) {
                        fuelNeedTotal += fuelNeed;
                        fuelNeed = fuelNeed / 3 - 2;
                    }
                    return fuelNeedTotal;
                })//
                .reduce(0, (a, b) -> a + b);

        logger.log(Level.INFO, () -> "sum  : " + getSum());
        logger.log(Level.INFO, () -> "sum2 : " + getSum2());

        return true;
    }

}
