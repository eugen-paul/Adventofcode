package net.eugenpaul.adventofcode.y2018.day17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day17 extends SolutionTemplate {
    private static final String SCAN_FORMAT = "^[x,y]=([0-9]*), [x,y]=([0-9]*)\\.\\.([0-9]*)$";
    private static final Pattern SCAN_PATTERN = Pattern.compile(SCAN_FORMAT);

    private enum Type {
        SAND('.'), CLAY('#'), WATER_SPRING('+'), WATTER_FLOWING('|'), WATER_STANDING('~');

        @Getter
        private final char value;

        private Type(char value) {
            this.value = value;
        }
    }

    @Getter
    private int tilesOfWater;
    @Getter
    private int tilesOfWaterLeft;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2018/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Type> tiles = initTiles(eventData);

        int yMin = tiles.keySet().stream().mapToInt(SimplePos::getY).min().getAsInt();
        int yMax = tiles.keySet().stream().mapToInt(SimplePos::getY).max().getAsInt();

        doPuzzle(tiles, new SimplePos(500, 1), yMax, Direction.S);

        tilesOfWater = (int) tiles.entrySet().stream()//
                .filter(v -> v.getKey().getY() >= yMin)//
                .map(Entry::getValue)//
                .filter(v -> v == Type.WATER_STANDING || v == Type.WATTER_FLOWING)//
                .count();

        tilesOfWaterLeft = (int) tiles.values().stream()//
                .filter(v -> v == Type.WATER_STANDING)//
                .count();

        logger.log(Level.INFO, () -> "tilesOfWater : " + getTilesOfWater());
        logger.log(Level.INFO, () -> "tilesOfWaterLeft : " + getTilesOfWaterLeft());

        return true;
    }

    private boolean doPuzzle(Map<SimplePos, Type> tiles, SimplePos pos, int yMax, Direction direction) {
        if (yMax < pos.getY()) {
            return false;
        }
        var posTile = tiles.get(pos);
        if (posTile == Type.CLAY) {
            return true;
        }

        if (posTile != null && posTile != Type.SAND) {
            return false;
        }

        tiles.put(pos, Type.WATTER_FLOWING);
        var nextTile = tiles.getOrDefault(pos.moveNew(Direction.S), Type.SAND);
        if (nextTile == Type.SAND) {
            doPuzzle(tiles, pos.moveNew(Direction.S), yMax, Direction.S);
        }

        nextTile = tiles.getOrDefault(pos.moveNew(Direction.S), Type.SAND);
        if (nextTile == Type.CLAY || nextTile == Type.WATER_STANDING) {
            var isWall = true;
            if (direction != Direction.W) {
                isWall &= doPuzzle(tiles, pos.moveNew(Direction.E), yMax, Direction.E);
            }
            if (direction != Direction.E) {
                isWall &= doPuzzle(tiles, pos.moveNew(Direction.W), yMax, Direction.W);
            }

            if (isWall && direction == Direction.S) {
                setWaterToStandWater(tiles, pos);
            }

            return isWall;
        }

        return false;
    }

    private void setWaterToStandWater(Map<SimplePos, Type> tiles, SimplePos pos) {
        SimplePos posOfStandingWater = pos;
        do {
            tiles.put(posOfStandingWater, Type.WATER_STANDING);
            posOfStandingWater = posOfStandingWater.moveNew(Direction.E);
        } while (tiles.get(posOfStandingWater) == Type.WATTER_FLOWING);

        posOfStandingWater = pos;
        do {
            tiles.put(posOfStandingWater, Type.WATER_STANDING);
            posOfStandingWater = posOfStandingWater.moveNew(Direction.W);
        } while (tiles.get(posOfStandingWater) == Type.WATTER_FLOWING);
    }

    private Map<SimplePos, Type> initTiles(List<String> eventData) {
        Map<SimplePos, Type> responseTiles = new HashMap<>();

        for (String scan : eventData) {
            Matcher m = SCAN_PATTERN.matcher(scan);
            int c;
            int from;
            int to;
            if (m.find()) {
                c = Integer.parseInt(m.group(1));
                from = Integer.parseInt(m.group(2));
                to = Integer.parseInt(m.group(3));
            } else {
                throw new IllegalArgumentException(scan);
            }

            if (scan.charAt(0) == 'x') {
                for (int i = from; i <= to; i++) {
                    responseTiles.put(new SimplePos(c, i), Type.CLAY);
                }
            } else {
                for (int i = from; i <= to; i++) {
                    responseTiles.put(new SimplePos(i, c), Type.CLAY);
                }
            }
        }

        return responseTiles;
    }

}
