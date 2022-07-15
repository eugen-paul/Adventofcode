package net.eugenpaul.adventofcode.y2020.day12;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day12 extends SolutionTemplate {

    @Getter
    private int distance;
    @Getter
    private int distance2;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2020/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        distance = doPuzzle1(eventData);
        distance2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "distance   : " + getDistance());
        logger.log(Level.INFO, () -> "distance2  : " + getDistance2());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        SimplePos pos = new SimplePos(0, 0);
        Direction direction = Direction.E;
        for (String data : eventData) {
            char action = getAction(data);
            int value = getValue(data);
            switch (action) {
            case 'N', 'S', 'E', 'W':
                move(pos, Direction.fromChar(action), value);
                break;
            case 'L':
                direction = turnLeft(direction, value);
                break;
            case 'R':
                direction = turnRight(direction, value);
                break;
            case 'F':
                move(pos, direction, value);
                break;
            default:
                break;
            }
        }

        return Math.abs(pos.getX()) + Math.abs(pos.getY());
    }

    private int doPuzzle2(List<String> eventData) {
        SimplePos pos = new SimplePos(0, 0);
        SimplePos waypointPos = new SimplePos(10, -1);
        for (String data : eventData) {
            char action = getAction(data);
            int value = getValue(data);
            switch (action) {
            case 'N', 'S', 'E', 'W':
                move(waypointPos, Direction.fromChar(action), value);
                break;
            case 'L':
                waypointPos = turnWaypointLeft(waypointPos, value);
                break;
            case 'R':
                waypointPos = turnWaypointRight(waypointPos, value);
                break;
            case 'F':
                moveToWaypoint(pos, waypointPos, value);
                break;
            default:
                break;
            }
        }

        return Math.abs(pos.getX()) + Math.abs(pos.getY());
    }

    private char getAction(String data) {
        return data.charAt(0);
    }

    private int getValue(String data) {
        return Integer.parseInt(data.substring(1));
    }

    private void move(SimplePos pos, Direction d, int value) {
        for (int i = 0; i < value; i++) {
            pos.move(d);
        }
    }

    private void moveToWaypoint(SimplePos pos, SimplePos wp, int value) {
        for (int i = 0; i < value; i++) {
            pos.add(wp);
        }
    }

    private Direction turnLeft(Direction d, int value) {
        int rest = value;
        Direction response = d;
        while (rest > 0) {
            response = response.turnLeft();
            rest -= 90;
        }
        return response;
    }

    private SimplePos turnWaypointLeft(SimplePos pos, int value) {
        int rest = value;
        SimplePos response = pos.copy();
        while (rest > 0) {
            response = new SimplePos(response.getY(), -response.getX());
            rest -= 90;
        }
        return response;
    }

    private SimplePos turnWaypointRight(SimplePos pos, int value) {
        int rest = value;
        SimplePos response = pos.copy();
        while (rest > 0) {
            response = new SimplePos(-response.getY(), response.getX());
            rest -= 90;
        }
        return response;
    }

    private Direction turnRight(Direction d, int value) {
        int rest = value;
        Direction response = d;
        while (rest > 0) {
            response = response.turnRight();
            rest -= 90;
        }
        return response;
    }

}
