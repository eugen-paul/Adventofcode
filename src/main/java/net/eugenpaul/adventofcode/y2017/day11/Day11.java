package net.eugenpaul.adventofcode.y2017.day11;

import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day11 extends SolutionTemplate {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Pos {
        private int x;
        private int y;
    }

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

        Pos currentPosition = new Pos(0, 0);
        maxDistance = 0;

        for (String direction : path) {
            computeNextPosition(currentPosition, direction);
            distance = computeDistance(currentPosition);
            maxDistance = Math.max(maxDistance, distance);
        }

        logger.log(Level.INFO, () -> "distance : " + getDistance());
        logger.log(Level.INFO, () -> "maxDistance : " + getMaxDistance());

        return true;
    }

    private void computeNextPosition(Pos position, String direction) {
        switch (direction) {
        case "n":
            position.setY(position.getY() + 1);
            break;
        case "ne":
            position.setX(position.getX() + 1);
            if ((position.getX() % 2) == 1 || (position.getX() % 2) == -1) {
                position.setY(position.getY() + 1);
            }
            break;
        case "nw":
            position.setX(position.getX() - 1);
            if ((position.getX() % 2) == 1 || (position.getX() % 2) == -1) {
                position.setY(position.getY() + 1);
            }
            break;
        case "se":
            position.setX(position.getX() + 1);
            if ((position.getX() % 2) == 0) {
                position.setY(position.getY() - 1);
            }
            break;
        case "sw":
            position.setX(position.getX() - 1);
            if ((position.getX() % 2) == 0) {
                position.setY(position.getY() - 1);
            }
            break;
        case "s":
            position.setY(position.getY() - 1);
            break;
        default:
            break;
        }
    }

    private int computeDistance(Pos position) {
        int x = Math.abs(position.getX());
        int y = Math.abs(position.getY());
        int cSteps = 0;

        if (position.getY() < 0) {
            cSteps = x + Math.max(0, y - x + (x + 1) / 2);
        } else {
            cSteps = x + Math.max(0, y - x + x / 2);
        }
        return cSteps;
    }

}
