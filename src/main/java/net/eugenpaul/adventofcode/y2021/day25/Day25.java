package net.eugenpaul.adventofcode.y2021.day25;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    @Getter
    private long steps;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2021/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Direction> area = MapOfSimplePos.initMap(eventData, v -> {
            try {
                return Direction.fromArrow(v);
            } catch (IllegalArgumentException e) {
                return null;
            }
        });

        steps = 0;
        boolean moved = false;
        final int maxX = eventData.get(0).length();
        final int maxY = eventData.size();

        do {
            Map<SimplePos, Direction> nextStep = new HashMap<>();

            moved = doStep(maxX, maxY, area, nextStep, Direction.E);

            area = nextStep;
            nextStep = new HashMap<>();

            moved = doStep(maxX, maxY, area, nextStep, Direction.S) || moved;

            area = nextStep;
            steps++;

        } while (moved);

        logger.log(Level.INFO, () -> "steps  : " + getSteps());

        return true;
    }

    private boolean doStep(int maxX, int maxY, Map<SimplePos, Direction> area, Map<SimplePos, Direction> nextStep, Direction currentDirection) {
        boolean moved = false;
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                var pos = new SimplePos(x, y);
                var type = area.get(pos);
                if (type == currentDirection) {
                    var newPos = coputeNewPos(maxX, maxY, pos, type);
                    if (!area.containsKey(newPos)) {
                        nextStep.put(newPos, type);
                        moved = true;
                    } else {
                        nextStep.put(pos, type);
                    }
                } else if (type != null) {
                    nextStep.put(pos, type);
                }
            }
        }
        return moved;
    }

    private SimplePos coputeNewPos(int maxX, int maxY, SimplePos pos, Direction type) {
        var newPos = pos.moveNew(type);
        if (newPos.getX() == maxX) {
            newPos.setX(0);
        }
        if (newPos.getY() == maxY) {
            newPos.setY(0);
        }
        return newPos;
    }
}
