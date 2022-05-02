package net.eugenpaul.adventofcode.y2018.day14;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.CircleMemory;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day14 extends SolutionTemplate {

    @Getter
    private String score;
    @Getter
    private Integer recipesBevorScore;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2018/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(Integer eventData) {

        score = doPuzzle1(eventData);
        recipesBevorScore = doPuzzle2(eventData);
        logger.log(Level.INFO, () -> "score : " + getScore());
        logger.log(Level.INFO, () -> "recipesBevorScore : " + getRecipesBevorScore());

        return true;
    }

    public String doPuzzle1(Integer eventData) {
        CircleMemory<Integer> memory = new CircleMemory<>();
        var pos1 = memory.addFirst(3);
        var pos2 = memory.add(7, pos1);

        StringBuilder scoreBuilder = new StringBuilder(10);

        boolean done = false;
        while (!done) {
            int sum = memory.getData(pos1) + memory.getData(pos2);
            if (sum >= 10) {
                int recipe1 = sum / 10;
                done = addRecipePuzzle1(memory, recipe1, scoreBuilder, eventData);

                int recipe2 = sum % 10;
                if (!done) {
                    done = addRecipePuzzle1(memory, recipe2, scoreBuilder, eventData);
                }
            } else {
                int recipe1 = sum;
                done = addRecipePuzzle1(memory, recipe1, scoreBuilder, eventData);
            }

            pos1 = memory.moveNext(pos1, memory.getData(pos1) + 1);
            pos2 = memory.moveNext(pos2, memory.getData(pos2) + 1);
        }

        return scoreBuilder.toString();
    }

    private int doPuzzle2(Integer eventData) {
        List<Integer> digitScore = new LinkedList<>();
        int d = eventData;
        while (d > 0) {
            digitScore.add(0, d % 10);
            d = d / 10;
        }

        CircleMemory<Integer> memory = new CircleMemory<>();
        var pos1 = memory.addFirst(3);
        var pos2 = memory.add(7, pos1);

        List<Integer> currentScore = new LinkedList<>();
        currentScore.add(3);
        currentScore.add(7);

        recipesBevorScore = null;

        boolean done = false;
        while (!done) {
            int sum = memory.getData(pos1) + memory.getData(pos2);
            if (sum >= 10) {
                int recipe1 = sum / 10;
                done = addRecipePuzzle2(memory, recipe1, digitScore, currentScore);

                int recipe2 = sum % 10;
                if (!done) {
                    done = addRecipePuzzle2(memory, recipe2, digitScore, currentScore);
                }
            } else {
                int recipe1 = sum;
                done = addRecipePuzzle2(memory, recipe1, digitScore, currentScore);
            }

            pos1 = memory.moveNext(pos1, memory.getData(pos1) + 1);
            pos2 = memory.moveNext(pos2, memory.getData(pos2) + 1);
        }

        return recipesBevorScore;
    }

    private boolean addRecipePuzzle1(CircleMemory<Integer> memory, int recipe, StringBuilder scoreBuilder, int scoreAfter) {
        memory.addLast(recipe);

        if (memory.getSize() > scoreAfter) {
            scoreBuilder.append(recipe);
        }

        return scoreBuilder.length() >= 10;
    }

    private boolean addRecipePuzzle2(CircleMemory<Integer> memory, int recipe, List<Integer> digitScore, List<Integer> currentScore) {
        memory.addLast(recipe);

        if (recipesBevorScore == null) {
            currentScore.add(recipe);
            while (currentScore.size() > digitScore.size()) {
                currentScore.remove(0);
            }

            if (currentScore.equals(digitScore)) {
                recipesBevorScore = memory.getSize() - digitScore.size();
                return true;
            }
        }

        return false;
    }

}
