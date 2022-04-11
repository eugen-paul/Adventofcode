package net.eugenpaul.adventofcode.y2015.day1;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private Integer firstPositionOfbasement = null;

    @Getter
    private Integer onFlor = null;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2015/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        int startFloor = 0;

        onFlor = startFloor;
        for (int i = 0; i < eventData.length(); i++) {
            char step = eventData.charAt(i);
            switch (step) {
            case '(':
                onFlor++;
                break;
            case ')':
                onFlor--;
                break;
            default:
                return false;
            }

            if (null == firstPositionOfbasement && -1 == onFlor) {
                firstPositionOfbasement = i + 1;
            }
        }
        logger.log(Level.INFO, () -> "Santa on flor " + onFlor);
        logger.log(Level.INFO, () -> "Enter the basement on step " + firstPositionOfbasement);
        return true;
    }
}
