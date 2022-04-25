package net.eugenpaul.adventofcode.y2015.day15;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day15 extends SolutionTemplate {

    @AllArgsConstructor
    @Getter
    private static class Ingredients {
        private int capacity;
        private int durability;
        private int flavor;
        private int texture;
        private int calories;

        public static Ingredients fromString(String data) {
            // Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
            String dataWithOutComma = data.replace(",", "");
            String[] elements = dataWithOutComma.split(" ");
            return new Ingredients(//
                    Integer.parseInt(elements[2]), //
                    Integer.parseInt(elements[4]), //
                    Integer.parseInt(elements[6]), //
                    Integer.parseInt(elements[8]), //
                    Integer.parseInt(elements[10]) //
            );
        }
    }

    @Getter
    private long maxTotalscore;
    @Getter
    private long maxTotalscoreWithCalorieLimit;

    private int calorie = 500;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2015/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        List<Ingredients> recipes = eventData.stream()//
                .map(Ingredients::fromString)//
                .collect(Collectors.toCollection(ArrayList::new));
        int[] teaspoons = new int[recipes.size()];

        findBestScore(recipes, teaspoons, 100, 0, calorie);

        logger.log(Level.INFO, () -> "maxTotalscore: " + getMaxTotalscore());
        logger.log(Level.INFO, () -> "maxTotalscoreWithCalorieLimit: " + getMaxTotalscoreWithCalorieLimit());
        return true;
    }

    private void findBestScore(List<Ingredients> ingredients, int[] teaspoons, int remainderTeaspoons, int recipePosition, int calorie) {
        if (recipePosition == ingredients.size() - 1) {
            teaspoons[recipePosition] = remainderTeaspoons;
            long score = computescore(ingredients, teaspoons);
            maxTotalscore = Math.max(maxTotalscore, score);

            long cookieCalorie = computeCalories(ingredients, teaspoons);
            if (cookieCalorie == calorie && maxTotalscoreWithCalorieLimit < score) {
                maxTotalscoreWithCalorieLimit = score;
            }
            return;
        }

        for (int i = remainderTeaspoons; i >= 0; i--) {
            teaspoons[recipePosition] = i;
            findBestScore(ingredients, teaspoons, remainderTeaspoons - i, recipePosition + 1, calorie);
        }
    }

    private long computescore(List<Ingredients> ingredients, int[] teaspoons) {
        long totalCapacity = 0l;
        long totalDurability = 0l;
        long totalFlavor = 0l;
        long totalTexture = 0l;
        for (int i = 0; i < ingredients.size(); i++) {
            totalCapacity += ingredients.get(i).getCapacity() * teaspoons[i];
            totalDurability += ingredients.get(i).getDurability() * teaspoons[i];
            totalFlavor += ingredients.get(i).getFlavor() * teaspoons[i];
            totalTexture += ingredients.get(i).getTexture() * teaspoons[i];
        }

        totalCapacity = Math.max(0, totalCapacity);
        totalDurability = Math.max(0, totalDurability);
        totalFlavor = Math.max(0, totalFlavor);
        totalTexture = Math.max(0, totalTexture);

        return totalCapacity * totalDurability * totalFlavor * totalTexture;
    }

    private long computeCalories(List<Ingredients> ingredients, int[] teaspoons) {
        long totalCalories = 0l;
        for (int i = 0; i < ingredients.size(); i++) {
            totalCalories += ingredients.get(i).getCalories() * teaspoons[i];
        }

        return Math.max(0, totalCalories);
    }

}
