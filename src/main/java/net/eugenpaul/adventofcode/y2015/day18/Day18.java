package net.eugenpaul.adventofcode.y2015.day18;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day18 extends SolutionTemplate {

    @Getter
    private int lightsOn;
    @Getter
    private int lightsBrokenOn;

    @Setter
    private int steps = 100;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2015/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        lightsOn = 0;
        lightsBrokenOn = 0;

        int xSize = eventData.get(0).length();
        int ySize = eventData.size();

        boolean[] lightsGrid = new boolean[xSize * ySize];
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                lightsGrid[x + y * ySize] = eventData.get(y).charAt(x) == '#';
            }
        }

        lightsOn = doPuzzle(steps, xSize, ySize, lightsGrid, false);
        lightsBrokenOn = doPuzzle(steps, xSize, ySize, lightsGrid, true);

        logger.log(Level.INFO, () -> "lightsOn: " + getLightsOn());
        logger.log(Level.INFO, () -> "lightsBrokenOn: " + getLightsBrokenOn());

        return true;
    }

    private int doPuzzle(int steps, int xSize, int ySize, boolean[] lightsGrid, boolean areCornersBrocken) {
        int responseLightsOn = 0;

        boolean[] workingGrid = lightsGrid;
        if (areCornersBrocken) {
            workingGrid = setCornersToOn(xSize, workingGrid);
        }
        for (int i = 0; i < steps; i++) {
            workingGrid = doStep(workingGrid, xSize, ySize);
            if (areCornersBrocken) {
                workingGrid = setCornersToOn(xSize, workingGrid);
            }
        }

        for (boolean b : workingGrid) {
            if (b) {
                responseLightsOn++;
            }
        }

        return responseLightsOn;
    }

    private boolean[] setCornersToOn(int xSize, boolean[] lightsGrid) {
        boolean[] response = lightsGrid.clone();
        response[0] = true;
        response[xSize - 1] = true;
        response[response.length - xSize] = true;
        response[response.length - 1] = true;
        return response;
    }

    private boolean[] doStep(boolean[] lights, int xSize, int ySize) {
        boolean[] response = lights.clone();

        for (int i = 0; i < lights.length; i++) {
            int neighborsOn = getNeighborsOnCount(lights, i, xSize, ySize);
            if (lights[i]) {
                if (2 == neighborsOn || 3 == neighborsOn) {
                    response[i] = true;
                } else {
                    response[i] = false;
                }
            } else {
                if (3 == neighborsOn) {
                    response[i] = true;
                } else {
                    response[i] = false;
                }
            }
        }

        return response;
    }

    private int getNeighborsOnCount(boolean[] lights, int pos, int xSize, int ySize) {

        // MAP on Neighbors:
        // 123
        // 8A4
        // 765
        int x = pos % ySize;
        int y = pos / ySize;

        // getNXPosition returns position of Neighbors in Array or -1 if Neighbors not exist.
        int posN1 = getN1Position(pos, ySize, x, y);
        int posN2 = getN2Position(pos, ySize, y);
        int posN3 = getN3Position(pos, xSize, ySize, x, y);
        int posN4 = getN4Position(pos, xSize, x);
        int posN5 = getN5Position(pos, xSize, ySize, x, y);
        int posN6 = getN6Position(pos, ySize, y);
        int posN7 = getN7Position(pos, ySize, x, y);
        int posN8 = getN8Position(pos, x);

        int neighborsOnCount = 0;
        neighborsOnCount += isLightOn(lights, posN1) ? 1 : 0;
        neighborsOnCount += isLightOn(lights, posN2) ? 1 : 0;
        neighborsOnCount += isLightOn(lights, posN3) ? 1 : 0;
        neighborsOnCount += isLightOn(lights, posN4) ? 1 : 0;
        neighborsOnCount += isLightOn(lights, posN5) ? 1 : 0;
        neighborsOnCount += isLightOn(lights, posN6) ? 1 : 0;
        neighborsOnCount += isLightOn(lights, posN7) ? 1 : 0;
        neighborsOnCount += isLightOn(lights, posN8) ? 1 : 0;
        return neighborsOnCount;
    }

    private int getN8Position(int pos, int x) {
        return (x > 0) ? pos - 1 : -1;
    }

    private int getN7Position(int pos, int ySize, int x, int y) {
        return (x > 0 && y < ySize - 1) ? pos - 1 + ySize : -1;
    }

    private int getN6Position(int pos, int ySize, int y) {
        return (y < ySize - 1) ? pos + ySize : -1;
    }

    private int getN5Position(int pos, int xSize, int ySize, int x, int y) {
        return (x < xSize - 1 && y < ySize - 1) ? pos + 1 + ySize : -1;
    }

    private int getN4Position(int pos, int xSize, int x) {
        return (x < xSize - 1) ? pos + 1 : -1;
    }

    private int getN3Position(int pos, int xSize, int ySize, int x, int y) {
        return (x < xSize - 1 && y > 0) ? pos + 1 - ySize : -1;
    }

    private int getN2Position(int pos, int ySize, int y) {
        return (y > 0) ? pos - ySize : -1;
    }

    private int getN1Position(int pos, int ySize, int x, int y) {
        return (x > 0 && y > 0) ? (pos - 1 - ySize) : -1;
    }

    private boolean isLightOn(boolean[] lights, int pos) {
        if (pos < 0 || pos >= lights.length) {
            return false;
        }
        return lights[pos];
    }

}
