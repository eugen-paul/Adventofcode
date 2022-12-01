package net.eugenpaul.adventofcode.y2022.day1;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private long mostCalories;
    @Getter
    private long top3Calories;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2022/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Integer> sortedTotalCalories = getReverseSortedTotalCalories(eventData);

        mostCalories = getTop(sortedTotalCalories, 1);
        top3Calories = getTop(sortedTotalCalories, 3);

        logger.log(Level.INFO, () -> "mostCalories : " + getMostCalories());
        logger.log(Level.INFO, () -> "top3Calories : " + getTop3Calories());

        return true;
    }

    private int getTop(List<Integer> eventData, int number) {
        int response = 0;
        int count = 0;

        var iterator = eventData.iterator();
        while (count < number && iterator.hasNext()) {
            response += iterator.next();
            count++;
        }
        if (count < number) {
            throw new IllegalArgumentException();
        }
        return response;
    }

    private List<Integer> getReverseSortedTotalCalories(List<String> eventData) {
        List<Integer> response = new LinkedList<>();

        var currentCalories = 0;

        for (var calories : eventData) {
            if (calories.isBlank()) {
                response.add(currentCalories);
                currentCalories = 0;
            } else {
                currentCalories += Integer.parseInt(calories);
            }
        }

        response.add(currentCalories);

        Collections.sort(response, Collections.reverseOrder());

        return response;
    }

}
