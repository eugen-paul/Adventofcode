package net.eugenpaul.adventofcode.y2015.day6.grid;

import java.util.Arrays;

import net.eugenpaul.adventofcode.y2015.day6.IGrid;

public class LightsGrid implements IGrid{

    private boolean[][] gridOnOff;

    public LightsGrid(int sizeX, int sizeY) {
        gridOnOff = new boolean[sizeX][sizeY];
        for (boolean[] lines : gridOnOff) {
            Arrays.fill(lines, false);
        }
    }

    public int getLitLightsCount() {
        int response = 0;
        for (boolean[] lines : gridOnOff) {
            for (boolean light : lines) {
                if (light) {
                    response++;
                }
            }
        }
        return response;
    }

    @Override
    public void turnOn(int fromX, int fromY, int toX, int toY) {
        for (int i = fromX; i <= toX; i++) {
            Arrays.fill(gridOnOff[i], fromY, toY + 1, true);
        }
    }

    @Override
    public void turnOff(int fromX, int fromY, int toX, int toY) {
        for (int i = fromX; i <= toX; i++) {
            Arrays.fill(gridOnOff[i], fromY, toY + 1, false);
        }
    }

    @Override
    public void toggle(int fromX, int fromY, int toX, int toY) {
        for (int i = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                gridOnOff[i][j] = !gridOnOff[i][j];
            }
        }
    }
}
