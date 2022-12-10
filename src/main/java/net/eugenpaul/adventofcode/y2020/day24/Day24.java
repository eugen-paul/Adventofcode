package net.eugenpaul.adventofcode.y2020.day24;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.HexagonalMapH;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.HexagonalMapH.HexMapHDirection;

public class Day24 extends SolutionTemplate {

    @Getter
    private int black;
    @Getter
    private int black2;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2020/day24/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        black = doPuzzle1(eventData);
        black2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "black  : " + getBlack());
        logger.log(Level.INFO, () -> "black2 : " + getBlack2());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        Set<SimplePos> blackTiles = initTiles(eventData);

        return blackTiles.size();
    }

    private int doPuzzle2(List<String> eventData) {
        Set<SimplePos> blackTiles = initTiles(eventData);

        for (int i = 1; i <= 100; i++) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (var tile : blackTiles) {
                minX = Math.min(minX, tile.getX());
                minY = Math.min(minY, tile.getY());
                maxX = Math.max(maxX, tile.getX());
                maxY = Math.max(maxY, tile.getY());
            }
            minX--;
            minY--;
            maxX++;
            maxY++;

            Set<SimplePos> currentDay = new HashSet<>();

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    computeFlipping(blackTiles, currentDay, y, x);
                }
            }

            blackTiles = currentDay;
        }

        return blackTiles.size();
    }

    private void computeFlipping(Set<SimplePos> blackTiles, Set<SimplePos> currentDay, int y, int x) {
        SimplePos pos = new SimplePos(x, y);
        int neighbors = getBlackNeighborsTiles(pos, blackTiles);
        if ((neighbors == 0 || neighbors > 2) && blackTiles.contains(pos)) {
            // black to white
        } else if (neighbors == 2 && !blackTiles.contains(pos)) {
            // white to black
            currentDay.add(pos);
        } else if (blackTiles.contains(pos)) {
            // stay black
            currentDay.add(pos);
        } else {
            // stay white
        }
    }

    private int getBlackNeighborsTiles(SimplePos pos, Set<SimplePos> blackTiles) {
        var neighbors = HexagonalMapH.getNeighbors(pos);
        return (int) neighbors.stream().filter(blackTiles::contains).count();
    }

    private Set<SimplePos> initTiles(List<String> eventData) {
        Set<SimplePos> blackTiles = new HashSet<>();

        for (String data : eventData) {
            var way = getWay(data);
            SimplePos currentPos = new SimplePos(0, 0);
            for (var step : way) {
                currentPos = HexagonalMapH.computeNextPosition(currentPos, step);
            }
            if (blackTiles.contains(currentPos)) {
                blackTiles.remove(currentPos);
            } else {
                blackTiles.add(currentPos);
            }
        }
        return blackTiles;
    }

    private List<HexMapHDirection> getWay(String data) {
        String rest = data;
        List<HexMapHDirection> response = new LinkedList<>();
        while (!rest.isEmpty()) {
            HexMapHDirection d = HexMapHDirection.fromString(rest);
            rest = rest.substring(d.getValue().length());
            response.add(d);
        }

        return response;
    }

}
