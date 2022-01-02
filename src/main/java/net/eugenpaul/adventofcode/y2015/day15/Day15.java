package net.eugenpaul.adventofcode.y2015.day15;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day15 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day15.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day15.class.getName());

    private long maxTotalscore;
    private long maxTotalscoreWithCalorieLimit;

    public long getMaxTotalscore() {
        return maxTotalscore;
    }

    public long getMaxTotalscoreWithCalorieLimit() {
        return maxTotalscoreWithCalorieLimit;
    }

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2015/day15/puzzle1.txt", "y2015/day15/puzzle1_totalCalorie.txt");
    }

    public boolean doPuzzleFromFile(String filename, String filenameCalorie) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        int calorie = Integer.parseInt(FileReaderHelper.readStringFromFile(filenameCalorie));

        return doPuzzleFromData(eventData, calorie);
    }

    public boolean doPuzzleFromData(List<String> eventData, int calorie) {
        if (!doEvent(eventData, calorie)) {
            return false;
        }

        logger.log(Level.INFO, () -> "maxTotalscore: " + getMaxTotalscore());
        logger.log(Level.INFO, () -> "maxTotalscoreWithCalorieLimit: " + getMaxTotalscoreWithCalorieLimit());

        return true;
    }

    private boolean doEvent(List<String> eventData, int calorie) {
        if (null == eventData) {
            return false;
        }

        List<Recipe> recipes = new ArrayList<>();
        for (String data : eventData) {
            recipes.add(Recipe.fromString(data));
        }
        int[] teaspoons = new int[recipes.size()];

        findBestScore(recipes, teaspoons, 100, 0, calorie);

        return true;
    }

    private void findBestScore(List<Recipe> recipes, int[] teaspoons, int remainderTeaspoons, int recipePosition, int calorie) {
        if (recipePosition == recipes.size() - 1) {
            teaspoons[recipePosition] = remainderTeaspoons;
            long score = computescore(recipes, teaspoons);
            if (maxTotalscore < score) {
                maxTotalscore = score;
            }

            long cookieCalorie = computeCalories(recipes, teaspoons);
            if (cookieCalorie == calorie && maxTotalscoreWithCalorieLimit < score) {
                maxTotalscoreWithCalorieLimit = score;
            }
            return;
        }

        for (int i = remainderTeaspoons; i >= 0; i--) {
            teaspoons[recipePosition] = i;
            findBestScore(recipes, teaspoons, remainderTeaspoons - i, recipePosition + 1, calorie);
        }
    }

    private long computescore(List<Recipe> recipes, int[] teaspoons) {
        long totalCapacity = 0l;
        long totalDurability = 0l;
        long totalFlavor = 0l;
        long totalTexture = 0l;
        for (int i = 0; i < recipes.size(); i++) {
            totalCapacity += recipes.get(i).getCapacity() * teaspoons[i];
            totalDurability += recipes.get(i).getDurability() * teaspoons[i];
            totalFlavor += recipes.get(i).getFlavor() * teaspoons[i];
            totalTexture += recipes.get(i).getTexture() * teaspoons[i];
        }

        if (totalCapacity < 0) {
            totalCapacity = 0;
        }
        if (totalDurability < 0) {
            totalDurability = 0;
        }
        if (totalFlavor < 0) {
            totalFlavor = 0;
        }
        if (totalTexture < 0) {
            totalTexture = 0;
        }

        return totalCapacity * totalDurability * totalFlavor * totalTexture;
    }

    private long computeCalories(List<Recipe> recipes, int[] teaspoons) {
        long totalCalories = 0l;
        for (int i = 0; i < recipes.size(); i++) {
            totalCalories += recipes.get(i).getCalories() * teaspoons[i];
        }

        if (totalCalories < 0) {
            totalCalories = 0;
        }

        return totalCalories;
    }

}
