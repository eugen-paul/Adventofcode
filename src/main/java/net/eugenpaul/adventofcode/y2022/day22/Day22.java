package net.eugenpaul.adventofcode.y2022.day22;

import java.util.HashMap;
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

        int startX = 0;
        int startY = 0;
        while (area.get(new SimplePos(startX, startY)) != Tile.OPEN) {
            startX++;
        }

        Direction facing = Direction.E;
        var pos = new SimplePos(startX, startY);

        String movePath = eventData.get(eventData.size() - 1);

        while (!movePath.isBlank()) {
            int moveLength = 0;
            String steps = "";
            for (int i = 0; i < movePath.length(); i++) {
                if (movePath.charAt(i) >= '0' && movePath.charAt(i) <= '9') {
                    steps += movePath.charAt(i);
                } else {
                    break;
                }
            }
            movePath = movePath.substring(steps.length());

            int stepsInt = Integer.parseInt(steps);
            for (int i = 0; i < stepsInt; i++) {
                pos = doMove(pos, facing, area);
            }

            if (!movePath.isBlank()) {
                char dr = movePath.charAt(0);
                movePath = movePath.substring(1);
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
            }

            // System.out.println(pos);
        }

        System.out.println(pos);
        System.out.println(facing);

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

        if (facing == Direction.N) {
            int startX = pos.getX();
            int startY = 300;
            while (area.get(new SimplePos(startX, startY)) == Tile.CLOSE || area.get(new SimplePos(startX, startY)) == null) {
                startY--;
            }
            if (area.get(new SimplePos(startX, startY)) == Tile.WALL) {
                return pos;
            }
            return new SimplePos(startX, startY);
        }

        if (facing == Direction.S) {
            int startX = pos.getX();
            int startY = 0;
            while (area.get(new SimplePos(startX, startY)) == Tile.CLOSE || area.get(new SimplePos(startX, startY)) == null) {
                startY++;
            }
            if (area.get(new SimplePos(startX, startY)) == Tile.WALL) {
                return pos;
            }
            return new SimplePos(startX, startY);
        }

        if (facing == Direction.E) {
            int startX = 0;
            int startY = pos.getY();
            while (area.get(new SimplePos(startX, startY)) == Tile.CLOSE || area.get(new SimplePos(startX, startY)) == null) {
                startX++;
            }
            if (area.get(new SimplePos(startX, startY)) == Tile.WALL) {
                return pos;
            }
            return new SimplePos(startX, startY);
        }

        int startX = 300;
        int startY = pos.getY();
        while (area.get(new SimplePos(startX, startY)) == Tile.CLOSE || area.get(new SimplePos(startX, startY)) == null) {
            startX--;
        }
        if (area.get(new SimplePos(startX, startY)) == Tile.WALL) {
            return pos;
        }
        return new SimplePos(startX, startY);
    }

    private long doPuzzle2(List<String> eventData) {

        Map<SimplePos, Tile> area = readMap(eventData);

        int startX = 0;
        int startY = 0;
        while (area.get(new SimplePos(startX, startY)) != Tile.OPEN) {
            startX++;
        }

        Direction facing = Direction.E;
        var pos = new SimplePos(startX, startY);

        String movePath = eventData.get(eventData.size() - 1);

        while (!movePath.isBlank()) {
            int moveLength = 0;
            String steps = "";
            for (int i = 0; i < movePath.length(); i++) {
                if (movePath.charAt(i) >= '0' && movePath.charAt(i) <= '9') {
                    steps += movePath.charAt(i);
                } else {
                    break;
                }
            }
            movePath = movePath.substring(steps.length());

            int stepsInt = Integer.parseInt(steps);
            for (int i = 0; i < stepsInt; i++) {
                var mf = doMove2(pos, facing, area);
                if(area.get(mf.pos) == Tile.CLOSE){
                    System.out.println();
                }
                if (mf.pos.getY() < 0) {
                    System.out.println();
                }
                pos = mf.pos;
                facing = mf.facing;
            }

            if (!movePath.isBlank()) {
                char dr = movePath.charAt(0);
                movePath = movePath.substring(1);
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
            }

            // System.out.println(pos);
        }

        System.out.println(pos);
        System.out.println(facing);

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

    @AllArgsConstructor
    private class MF {
        SimplePos pos;
        Direction facing;
    }

    private MF doMove2(SimplePos pos, Direction facing, Map<SimplePos, Tile> area) {
        SimplePos newPos = pos.moveNew(facing);
        if (area.get(newPos) == Tile.OPEN) {
            return new MF(newPos, facing);
        }
        if (area.get(newPos) == Tile.WALL) {
            return new MF(pos, facing);
        }

        if (facing == Direction.N) {

            if (pos.getX() >= 0 && pos.getX() <= 49) {
                Direction nf = Direction.E;
                SimplePos np = new SimplePos(50, pos.getX() + 50);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

            if (pos.getX() >= 50 && pos.getX() <= 99) {
                Direction nf = Direction.E;
                SimplePos np = new SimplePos(0, pos.getX() - 50 + 150);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

            if (pos.getX() >= 100 && pos.getX() <= 149) {
                Direction nf = Direction.N;
                SimplePos np = new SimplePos(pos.getX() - 100, 199);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

        }

        if (facing == Direction.S) {

            if (pos.getX() >= 0 && pos.getX() <= 49) {
                Direction nf = Direction.S;
                SimplePos np = new SimplePos(pos.getX() + 100, 0);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

            if (pos.getX() >= 50 && pos.getX() <= 99) {
                Direction nf = Direction.W;
                SimplePos np = new SimplePos(49, pos.getX() - 50 + 150);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

            if (pos.getX() >= 100 && pos.getX() <= 149) {
                Direction nf = Direction.W;
                SimplePos np = new SimplePos(99, pos.getX() - 100 + 50);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }
        }

        if (facing == Direction.E) {
            if (pos.getY() >= 0 && pos.getY() <= 49) {
                Direction nf = Direction.W;
                SimplePos np = new SimplePos(99, (49 - pos.getY()) + 100);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

            if (pos.getY() >= 50 && pos.getY() <= 99) {
                Direction nf = Direction.N;
                SimplePos np = new SimplePos(pos.getY() - 50 + 100, 49);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

            if (pos.getY() >= 100 && pos.getY() <= 149) {
                Direction nf = Direction.W;
                SimplePos np = new SimplePos(149, 49 - (pos.getY() - 100));
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }

            if (pos.getY() >= 150 && pos.getY() <= 199) {
                Direction nf = Direction.N;
                SimplePos np = new SimplePos(pos.getY() - 150 + 50, 149);
                if (area.get(np) == Tile.WALL) {
                    return new MF(pos, facing);
                }
                return new MF(np, nf);
            }
        }

        if (pos.getY() >= 0 && pos.getY() <= 49) {
            Direction nf = Direction.E;
            SimplePos np = new SimplePos(0, (49 - pos.getY()) + 100);
            if (area.get(np) == Tile.WALL) {
                return new MF(pos, facing);
            }
            return new MF(np, nf);
        }

        if (pos.getY() >= 50 && pos.getY() <= 99) {
            Direction nf = Direction.S;
            SimplePos np = new SimplePos(pos.getY() - 50, 100);
            if (area.get(np) == Tile.WALL) {
                return new MF(pos, facing);
            }
            return new MF(np, nf);
        }

        if (pos.getY() >= 100 && pos.getY() <= 149) {
            Direction nf = Direction.E;
            SimplePos np = new SimplePos(50, 49 - (pos.getY() - 100));
            if (area.get(np) == Tile.WALL) {
                return new MF(pos, facing);
            }
            return new MF(np, nf);
        }

        if (pos.getY() >= 150 && pos.getY() <= 199) {
            Direction nf = Direction.S;
            SimplePos np = new SimplePos(pos.getY() - 150 + 50, 0);
            if (area.get(np) == Tile.WALL) {
                return new MF(pos, facing);
            }
            return new MF(np, nf);
        }

        throw new IllegalArgumentException();
    }

    private Map<SimplePos, Tile> readMap(List<String> eventData) {

        List<String> m = new LinkedList<>();
        for (String string : eventData) {
            if (string.isBlank()) {
                break;
            }
            m.add(string);
        }

        Map<SimplePos, Tile> area = MapOfSimplePos.initMap(m, //
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

        return area;
    }
}
