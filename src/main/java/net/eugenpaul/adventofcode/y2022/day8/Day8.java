package net.eugenpaul.adventofcode.y2022.day8;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day8 extends SolutionTemplate {

    @Getter
    private int visibleTrees;
    @Getter
    private int highestScenicScore;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2022/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var trees = MapOfSimplePos.initMapOfDigits(eventData);

        visibleTrees = doPuzzle1(trees);
        highestScenicScore = doPuzzle2(trees);

        logger.log(Level.INFO, () -> "visibleTrees : " + getVisibleTrees());
        logger.log(Level.INFO, () -> "highestScenicScore : " + getHighestScenicScore());

        return true;
    }

    private int doPuzzle1(Map<SimplePos, Integer> trees) {
        return (int) trees.entrySet().stream()//
                // remove unvisible nodes
                .filter(v -> isVisible(trees, v.getKey()))//
                .count();
    }

    private int doPuzzle2(Map<SimplePos, Integer> trees) {
        return trees.entrySet().stream()//
                // compute score for each node
                .mapToInt(v -> getScoreOfTree(trees, v.getKey()))//
                .max().orElseThrow();
    }

    private int getScoreOfTree(Map<SimplePos, Integer> trees, SimplePos pos) {
        return Stream.of(Direction.values())//
                .mapToInt(v -> getScoreOfTree(trees, pos, v))//
                .reduce(1, (a, b) -> a * b);
    }

    private int getScoreOfTree(Map<SimplePos, Integer> trees, SimplePos pos, Direction dir) {
        SimplePos currentPos = pos;
        int treeHeight = trees.get(pos);
        int score = 0;

        while (true) {
            currentPos = currentPos.moveNew(dir);
            var currentH = trees.get(currentPos);
            if (currentH == null) {
                // out of map
                return score;
            }
            if (currentH >= treeHeight) {
                // last visible tree
                score++;
                return score;
            }
            score++;
        }
    }

    private boolean isVisible(Map<SimplePos, Integer> trees, SimplePos pos) {
        return Stream.of(Direction.values())//
                .anyMatch(v -> isVisible(trees, pos, v));
    }

    private boolean isVisible(Map<SimplePos, Integer> trees, SimplePos pos, Direction dir) {
        SimplePos currentPos = pos;
        int treeHeight = trees.get(currentPos);

        while (true) {
            currentPos = currentPos.moveNew(dir);
            var height = trees.get(currentPos);
            if (height == null) {
                // out of map
                return true;
            }
            if (height >= treeHeight) {
                // current tree is higher than given tree
                return false;
            }
        }
    }

}
