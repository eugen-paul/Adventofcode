package net.eugenpaul.adventofcode.y2017.day19;

import java.util.List;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @AllArgsConstructor
    @Data
    private class Pos {
        private int x;
        private int y;
        private Direction direction;
    }

    @Getter
    private String letters;
    @Getter
    private int counter;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2017/day19/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Pos pos = getStart(eventData);

        StringBuilder way = new StringBuilder();
        counter = 0;

        while (true) {
            doStep(eventData, pos);
            counter++;

            char currentPos = eventData.get(pos.getY()).charAt(pos.getX());
            if (currentPos >= 'A' && currentPos <= 'Z') {
                way.append(currentPos);
            }

            if (currentPos == ' ') {
                break;
            }
        }

        letters = way.toString();

        logger.log(Level.INFO, () -> "letters : " + getLetters());
        logger.log(Level.INFO, () -> "counter : " + getCounter());

        return true;
    }

    private void doStep(List<String> eventData, Pos pos) {
        char currentPos = eventData.get(pos.getY()).charAt(pos.getX());

        if (currentPos != '+') {
            switch (pos.direction) {
            case DOWN:
                pos.setY(pos.getY() + 1);
                break;
            case UP:
                pos.setY(pos.getY() - 1);
                break;
            case LEFT:
                pos.setX(pos.getX() - 1);
                break;
            case RIGHT:
                pos.setX(pos.getX() + 1);
                break;
            default:
                break;
            }
        } else {
            switch (pos.direction) {
            case DOWN, UP:
                if (eventData.get(pos.getY()).charAt(pos.getX() - 1) != ' ') {
                    pos.setX(pos.getX() - 1);
                    pos.setDirection(Direction.LEFT);
                } else {
                    pos.setX(pos.getX() + 1);
                    pos.setDirection(Direction.RIGHT);
                }
                break;
            case LEFT, RIGHT:
                if (eventData.get(pos.getY() - 1).charAt(pos.getX()) != ' ') {
                    pos.setY(pos.getY() - 1);
                    pos.setDirection(Direction.UP);
                } else {
                    pos.setY(pos.getY() + 1);
                    pos.setDirection(Direction.DOWN);
                }
                break;
            default:
                break;
            }
        }

    }

    private Pos getStart(List<String> eventData) {
        String upperLine = eventData.get(0);
        for (int x = 0; x < upperLine.length(); x++) {
            if (upperLine.charAt(x) == '|') {
                return new Pos(x, 0, Direction.DOWN);
            }
        }

        String lowerLine = eventData.get(eventData.size() - 1);
        for (int x = 0; x < lowerLine.length(); x++) {
            if (lowerLine.charAt(x) == '|') {
                return new Pos(x, 0, Direction.UP);
            }
        }

        for (int y = 0; y < eventData.size(); y++) {
            if (eventData.get(y).charAt(0) == '-') {
                return new Pos(0, y, Direction.RIGHT);
            }
            if (eventData.get(y).charAt(eventData.get(y).length() - 1) == '-') {
                return new Pos(0, y, Direction.LEFT);
            }
        }

        throw new IllegalArgumentException("StartPoint cann't be found.");
    }

}
