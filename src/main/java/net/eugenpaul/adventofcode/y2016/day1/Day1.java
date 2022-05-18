package net.eugenpaul.adventofcode.y2016.day1;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private int distance;
    @Getter
    private Integer distancePuzzle2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2016/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        String[] turns = eventData.split(",");

        SimplePos pos = new SimplePos(0, 0);
        Set<SimplePos> locations = new HashSet<>();
        distancePuzzle2 = null;

        Direction direction = Direction.N;

        for (String turn : turns) {
            int range = Integer.parseInt(turn.trim().substring(1));

            if (turn.trim().charAt(0) == 'L') {
                direction = direction.turnLeft();
            } else {
                direction = direction.turnRight();
            }

            for (int i = 1; i <= range; i++) {
                pos = pos.moveNew(direction);
                saveAndCheckLocationPoint(locations, pos);
            }
        }

        distance = Math.abs(pos.getX()) + Math.abs(pos.getY());

        logger.log(Level.INFO, () -> "distance to Easter Bunny HQ: " + getDistance());
        logger.log(Level.INFO, () -> "distance from first location you visit twice to Easter Bunny HQ: " + getDistancePuzzle2());

        return true;
    }

    private void saveAndCheckLocationPoint(Set<SimplePos> locations, SimplePos pos) {
        if (null != distancePuzzle2) {
            return;
        }

        if (locations.contains(pos)) {
            distancePuzzle2 = Math.abs(pos.getX()) + Math.abs(pos.getY());
            return;
        }
        locations.add(pos);
    }
}
