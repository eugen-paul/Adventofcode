package net.eugenpaul.adventofcode.y2020.day3;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day3 extends SolutionTemplate {

    @Getter
    private long trees;
    @Getter
    private long treesMult;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2020/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        trees = getTrees(eventData, 3, 1);

        treesMult = getTrees(eventData, 1, 1);
        treesMult *= trees;
        treesMult *= getTrees(eventData, 5, 1);
        treesMult *= getTrees(eventData, 7, 1);
        treesMult *= getTrees(eventData, 1, 2);

        logger.log(Level.INFO, () -> "trees     : " + getTrees());
        logger.log(Level.INFO, () -> "treesMult : " + getTreesMult());

        return true;
    }

    private int getTrees(List<String> eventData, int deltaX, int deltaY) {
        int len = eventData.get(0).length();
        Set<SimplePos> area = StringConverter.toSet(eventData, '#');

        int response = 0;

        for (int y = 0; y < eventData.size(); y += deltaY) {
            if (area.contains(new SimplePos((y * deltaX / deltaY) % len, y))) {
                response++;
            }
        }

        return response;
    }

}
