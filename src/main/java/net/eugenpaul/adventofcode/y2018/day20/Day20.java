package net.eugenpaul.adventofcode.y2018.day20;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day20 extends SolutionTemplate {

    @Getter
    private long largestNumberOfDoors;
    @Getter
    private long locationsAtLeast1000Doors;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2018/day20/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        Map<SimplePos, Boolean> area = new HashMap<>();

        area.put(new SimplePos(0, 0), true);
        fillMap(area, eventData, 1, 0, 0);

        doPuzzle(area);

        logger.log(Level.INFO, () -> "largestNumberOfDoors : " + getLargestNumberOfDoors());
        logger.log(Level.INFO, () -> "locationsAtLeast1000Doors : " + getLocationsAtLeast1000Doors());

        return true;
    }

    private int fillMap(Map<SimplePos, Boolean> area, String eventData, int pos, int currentX, int currentY) {

        int currentReadPos = pos;
        SimplePos currentMapPos = new SimplePos(currentX, currentY);
        SimplePos firstMapPos = new SimplePos(currentX, currentY);

        while (currentReadPos < eventData.length() - 1) {
            char c = eventData.charAt(currentReadPos);

            switch (c) {
            case 'N', 'W', 'E', 'S':
                currentMapPos = currentMapPos.moveNew(Direction.fromChar(c));
                area.put(currentMapPos, true);
                currentMapPos = currentMapPos.moveNew(Direction.fromChar(c));
                area.put(currentMapPos, true);
                break;
            case '(':
                currentReadPos = fillMap(area, eventData, currentReadPos + 1, currentMapPos.getX(), currentMapPos.getY());
                break;
            case ')':
                return currentReadPos;
            case '|':
                currentMapPos = firstMapPos;
                break;
            default:
                break;
            }

            currentReadPos++;
        }

        return currentReadPos;
    }

    private void doPuzzle(Map<SimplePos, Boolean> area) {
        SimplePos lastPos = new SimplePos(0, 0);

        Map<SimplePos, Integer> visitedRooms = new HashMap<>();
        visitedRooms.put(lastPos, 0);

        LinkedList<SimplePos> nextSteps = new LinkedList<>();
        nextSteps.add(lastPos);

        while (!nextSteps.isEmpty()) {
            lastPos = nextSteps.pollFirst();
            nextSteps.addAll(getSteps(area, visitedRooms, lastPos));
        }

        largestNumberOfDoors = visitedRooms.get(lastPos) / 2;

        locationsAtLeast1000Doors = visitedRooms.values().stream()//
                .filter(v -> v / 2 >= 1000)//
                .filter(v -> v % 2 == 0)//
                .count();
    }

    private List<SimplePos> getSteps(Map<SimplePos, Boolean> area, Map<SimplePos, Integer> visitedRooms, SimplePos pos) {
        List<SimplePos> responseSteps = new LinkedList<>();
        Integer currentCost = visitedRooms.get(pos);

        for (var d : Direction.values()) {
            SimplePos step = pos.moveNew(d);
            Boolean valueN = area.get(step);
            if (valueN != null && valueN.booleanValue() && visitedRooms.get(step) == null) {
                responseSteps.add(step);
                visitedRooms.put(step, currentCost + 1);
            }
        }
        return responseSteps;

    }
}
