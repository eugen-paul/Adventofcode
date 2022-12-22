package net.eugenpaul.adventofcode.y2022.day22;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    private enum Tile {
        CLOSE, OPEN, WALL,
    }

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2022/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {

        Map<SimplePos, Tile> area = readMap(eventData);

        var pos = getStartPoint(area);

        Direction facing = Direction.E;

        String movePath = eventData.get(eventData.size() - 1);

        while (!movePath.isBlank()) {
            StringBuilder steps = readTilesToMove(movePath);
            movePath = movePath.substring(steps.length());

            int stepsInt = Integer.parseInt(steps.toString());
            for (int i = 0; i < stepsInt; i++) {
                pos = doMove(pos, facing, area);
            }

            if (!movePath.isBlank()) {
                char dr = movePath.charAt(0);
                movePath = movePath.substring(1);
                facing = doTurn(facing, dr);
            }
        }

        return computeResult(pos, facing);
    }

    private Direction doTurn(Direction facing, char dr) {
        switch (dr) {
        case 'R':
            facing = facing.turnRight();
            break;
        case 'L':
            facing = facing.turnLeft();
            break;
        default:
            throw new IllegalArgumentException(dr + "");
        }
        return facing;
    }

    private StringBuilder readTilesToMove(String movePath) {
        StringBuilder steps = new StringBuilder();
        for (int i = 0; i < movePath.length(); i++) {
            if (movePath.charAt(i) >= '0' && movePath.charAt(i) <= '9') {
                steps.append(movePath.charAt(i));
            } else {
                break;
            }
        }
        return steps;
    }

    private SimplePos getStartPoint(Map<SimplePos, Tile> area) {
        int startX = 0;
        int startY = 0;
        while (area.get(new SimplePos(startX, startY)) != Tile.OPEN) {
            startX++;
        }
        return new SimplePos(startX, startY);
    }

    private long computeResult(SimplePos pos, Direction facing) {
        int f = 0;
        switch (facing) {
        case E:
            f = 0;
            break;
        case S:
            f = 1;
            break;
        case W:
            f = 2;
            break;
        case N:
            f = 3;
            break;
        default:
            break;
        }

        return 1000 * (pos.getY() + 1) + 4 * (pos.getX() + 1) + (long) f;
    }

    private SimplePos doMove(SimplePos pos, Direction facing, Map<SimplePos, Tile> area) {
        SimplePos newPos = pos.moveNew(facing);
        if (area.get(newPos) == Tile.OPEN) {
            return newPos;
        }

        if (area.get(newPos) == Tile.WALL) {
            return pos;
        }

        SimplePos testPos;
        switch (facing) {
        case N:
            testPos = new SimplePos(pos.getX(), 200);
            break;
        case S:
            testPos = new SimplePos(pos.getX(), 0);
            break;
        case W:
            testPos = new SimplePos(200, pos.getY());
            break;
        case E:
            testPos = new SimplePos(0, pos.getY());
            break;
        default:
            throw new IllegalArgumentException();
        }

        while (area.getOrDefault(testPos, Tile.CLOSE) == Tile.CLOSE) {
            testPos.move(facing);
        }
        if (area.get(testPos) == Tile.WALL) {
            return pos;
        }
        return testPos;
    }

    private long doPuzzle2(List<String> eventData) {

        Map<SimplePos, Tile> area = readMap(eventData);

        var pos = getStartPoint(area);

        Direction facing = Direction.E;

        String movePath = eventData.get(eventData.size() - 1);

        while (!movePath.isBlank()) {
            StringBuilder steps = readTilesToMove(movePath);
            movePath = movePath.substring(steps.length());

            int stepsInt = Integer.parseInt(steps.toString());
            for (int i = 0; i < stepsInt; i++) {
                var mf = doMove2(pos, facing, area);
                pos = mf.pos;
                facing = mf.facing;
            }

            if (!movePath.isBlank()) {
                char dr = movePath.charAt(0);
                movePath = movePath.substring(1);
                facing = doTurn(facing, dr);
            }
        }

        return computeResult(pos, facing);
    }

    @AllArgsConstructor
    private class PosFacReturn {
        SimplePos pos;
        Direction facing;
    }

    private PosFacReturn doMove2(SimplePos pos, Direction facing, Map<SimplePos, Tile> area) {
        SimplePos newPos = pos.moveNew(facing);
        if (area.get(newPos) == Tile.OPEN) {
            return new PosFacReturn(newPos, facing);
        }
        if (area.get(newPos) == Tile.WALL) {
            return new PosFacReturn(pos, facing);
        }

        Direction newFacing = null;
        SimplePos posAfterWrap = null;
        if (facing == Direction.N) {
            if (pos.getX() >= 0 && pos.getX() <= 49) {
                newFacing = Direction.E;
                posAfterWrap = new SimplePos(50, pos.getX() + 50);
            }

            if (pos.getX() >= 50 && pos.getX() <= 99) {
                newFacing = Direction.E;
                posAfterWrap = new SimplePos(0, pos.getX() - 50 + 150);
            }

            if (pos.getX() >= 100 && pos.getX() <= 149) {
                newFacing = Direction.N;
                posAfterWrap = new SimplePos(pos.getX() - 100, 199);
            }
        }

        if (facing == Direction.S) {
            if (pos.getX() >= 0 && pos.getX() <= 49) {
                newFacing = Direction.S;
                posAfterWrap = new SimplePos(pos.getX() + 100, 0);
            }

            if (pos.getX() >= 50 && pos.getX() <= 99) {
                newFacing = Direction.W;
                posAfterWrap = new SimplePos(49, pos.getX() - 50 + 150);
            }

            if (pos.getX() >= 100 && pos.getX() <= 149) {
                newFacing = Direction.W;
                posAfterWrap = new SimplePos(99, pos.getX() - 100 + 50);
            }
        }

        if (facing == Direction.E) {
            if (pos.getY() >= 0 && pos.getY() <= 49) {
                newFacing = Direction.W;
                posAfterWrap = new SimplePos(99, (49 - pos.getY()) + 100);
            }

            if (pos.getY() >= 50 && pos.getY() <= 99) {
                newFacing = Direction.N;
                posAfterWrap = new SimplePos(pos.getY() - 50 + 100, 49);
            }

            if (pos.getY() >= 100 && pos.getY() <= 149) {
                newFacing = Direction.W;
                posAfterWrap = new SimplePos(149, 49 - (pos.getY() - 100));
            }

            if (pos.getY() >= 150 && pos.getY() <= 199) {
                newFacing = Direction.N;
                posAfterWrap = new SimplePos(pos.getY() - 150 + 50, 149);
            }
        }

        if (facing == Direction.W) {
            if (pos.getY() >= 0 && pos.getY() <= 49) {
                newFacing = Direction.E;
                posAfterWrap = new SimplePos(0, (49 - pos.getY()) + 100);
            }

            if (pos.getY() >= 50 && pos.getY() <= 99) {
                newFacing = Direction.S;
                posAfterWrap = new SimplePos(pos.getY() - 50, 100);
            }

            if (pos.getY() >= 100 && pos.getY() <= 149) {
                newFacing = Direction.E;
                posAfterWrap = new SimplePos(50, 49 - (pos.getY() - 100));
            }

            if (pos.getY() >= 150 && pos.getY() <= 199) {
                newFacing = Direction.S;
                posAfterWrap = new SimplePos(pos.getY() - 150 + 50, 0);
            }
        }

        if (posAfterWrap == null || newFacing == null) {
            throw new IllegalArgumentException();
        }

        if (area.get(posAfterWrap) == Tile.WALL) {
            return new PosFacReturn(pos, facing);
        }
        return new PosFacReturn(posAfterWrap, newFacing);
    }

    private Map<SimplePos, Tile> readMap(List<String> eventData) {

        List<String> m = new LinkedList<>();
        for (String string : eventData) {
            if (string.isBlank()) {
                break;
            }
            m.add(string);
        }

        return MapOfSimplePos.initMap(m, //
                v -> {
                    switch (v) {
                    case ' ':
                        return Tile.CLOSE;
                    case '.':
                        return Tile.OPEN;
                    case '#':
                        return Tile.WALL;
                    default:
                        throw new IllegalArgumentException(v + "");
                    }
                });
    }
}
