package net.eugenpaul.adventofcode.y2019.day3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    @Getter
    private int distance;
    @Getter
    private int mitStepsToIntersection;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2019/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        doPuzzle(eventData);
        logger.log(Level.INFO, () -> "distance  : " + getDistance());
        logger.log(Level.INFO, () -> "mitStepsToIntersection  : " + getMitStepsToIntersection());

        return true;
    }

    private void doPuzzle(List<String> eventData) {
        Map<SimplePos, Integer> grid = new HashMap<>();

        distance = Integer.MAX_VALUE;
        mitStepsToIntersection = Integer.MAX_VALUE;

        addWireToMap(eventData.get(0), grid);
        checkIntersections(eventData.get(1), grid);
    }

    private void addWireToMap(String wire, Map<SimplePos, Integer> grid) {
        SimplePos pos = new SimplePos(0, 0);
        String[] data = wire.split(",");
        int delay = 0;
        for (String step : data) {
            Direction direction = Direction.fromUdrl(step.charAt(0));
            int length = Integer.parseInt(step.substring(1));

            for (int i = 0; i < length; i++) {
                delay++;
                pos = pos.moveNew(direction);
                grid.put(pos, delay);
            }
        }
    }

    private void checkIntersections(String wire, Map<SimplePos, Integer> grid) {
        SimplePos pos = new SimplePos(0, 0);
        String[] data = wire.split(",");
        int delay = 0;
        for (String step : data) {
            Direction direction = Direction.fromUdrl(step.charAt(0));
            int length = Integer.parseInt(step.substring(1));

            for (int i = 0; i < length; i++) {
                delay++;
                pos = pos.moveNew(direction);

                Integer oldDelay = grid.get(pos);
                if (oldDelay != null) {
                    distance = Math.min(//
                            distance, //
                            Math.abs(pos.getX()) + Math.abs(pos.getY())//
                    );

                    mitStepsToIntersection = Math.min(//
                            mitStepsToIntersection, //
                            oldDelay + delay//
                    );
                }
            }
        }
    }

}
