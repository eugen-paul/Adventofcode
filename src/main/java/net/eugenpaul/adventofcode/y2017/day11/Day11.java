package net.eugenpaul.adventofcode.y2017.day11;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.HexagonalMapV;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day11 extends SolutionTemplate {

    @Getter
    private Integer distance;
    @Getter
    private Integer maxDistance;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2017/day11/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        String[] path = eventData.split(",");

        SimplePos currentPosition = new SimplePos(0, 0);
        maxDistance = 0;

        for (String direction : path) {
            currentPosition = HexagonalMapV.computeNextPosition(currentPosition, direction);
            distance = HexagonalMapV.computeDistanceToZero(currentPosition);
            maxDistance = Math.max(maxDistance, distance);
        }

        logger.log(Level.INFO, () -> "distance : " + getDistance());
        logger.log(Level.INFO, () -> "maxDistance : " + getMaxDistance());

        return true;
    }

}
