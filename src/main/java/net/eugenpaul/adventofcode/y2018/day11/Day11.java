package net.eugenpaul.adventofcode.y2018.day11;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day11 extends SolutionTemplate {

    @Getter
    private String bestPos;

    @Getter
    private String bestPosEver;

    private int[][] grid = new int[300][300];

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2018/day11/puzzle1.txt");
    }

    @Override
    public boolean doEvent(Integer eventData) {

        computeGrid(eventData);

        SimplePos pos = findBest(3);
        bestPos = pos.getX() + "," + pos.getY();

        bestPosEver = findBestEver();

        logger.log(Level.INFO, () -> "bestPos : " + getBestPos());
        logger.log(Level.INFO, () -> "bestPosEver : " + getBestPosEver());

        return true;
    }

    public void computeGrid(Integer eventData) {
        for (int x = 1; x <= 300; x++) {
            long rackId = x + 10L;

            for (int y = 1; y <= 300; y++) {
                long powerLevel = rackId * y;
                powerLevel += eventData;
                powerLevel *= rackId;

                powerLevel = (powerLevel % 1000) / 100;

                powerLevel -= 5;

                grid[x - 1][y - 1] = (int) powerLevel;
            }
        }
    }

    public SimplePos findBest(int size) {
        int responseMaxPower = Integer.MIN_VALUE;
        int bestX = 0;
        int bestY = 0;
        for (int x = 1; x <= 300 - size + 1; x++) {
            for (int y = 1; y <= 300 - size + 1; y++) {
                int cellPower = getPowerValue(x, y, size);

                if (responseMaxPower < cellPower) {
                    responseMaxPower = cellPower;
                    bestX = x;
                    bestY = y;
                }
            }
        }

        return new SimplePos(bestX, bestY);
    }

    private int getPowerValue(int xPos, int yPos, int size) {
        int response = 0;

        for (int x = xPos; x < xPos + size; x++) {
            for (int y = yPos; y < yPos + size; y++) {
                response += grid[x - 1][y - 1];
            }
        }

        return response;
    }

    public String findBestEver() {
        int maxPower = Integer.MIN_VALUE;
        int maxSize = 1;
        SimplePos maxPos = new SimplePos(0, 0);

        for (int i = 1; i < 300; i++) {
            SimplePos pos = findBest(i);
            int power = getPowerValue(pos.getX(), pos.getY(), i);

            if (maxPower < power) {
                maxPower = power;
                maxPos = pos;
                maxSize = i;
            }
        }

        return maxPos.getX() + "," + maxPos.getY() + "," + maxSize;
    }

    public int getPowerLevel(SimplePos pos) {
        return grid[pos.getX() - 1][pos.getY() - 1];
    }

}
