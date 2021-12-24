package net.adventofcode.y2015.day6.grid;

import java.util.Arrays;

import net.adventofcode.y2015.day6.IGrid;

public class BrightnesGrid implements IGrid {

    private int[][] gridBrightness;

    public BrightnesGrid(int sizeX, int sizeY) {
        gridBrightness = new int[sizeX][sizeY];
        for (int[] lines : gridBrightness) {
            Arrays.fill(lines, 0);
        }
    }

    public int getBrightness() {
        int response = 0;
        for (int[] lines : gridBrightness) {
            for (int light : lines) {
                response += light;
            }
        }
        return response;
    }

    @Override
    public void turnOn(int fromX, int fromY, int toX, int toY) {
        for (int i = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                gridBrightness[i][j] = gridBrightness[i][j] + 1;
            }
        }
    }

    @Override
    public void turnOff(int fromX, int fromY, int toX, int toY) {
        for (int i = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                if (gridBrightness[i][j] <= 0) {
                    gridBrightness[i][j] = 0;
                } else {
                    gridBrightness[i][j] = gridBrightness[i][j] - 1;
                }
            }
        }
    }

    @Override
    public void toggle(int fromX, int fromY, int toX, int toY) {
        for (int i = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                gridBrightness[i][j] = gridBrightness[i][j] + 2;
            }
        }
    }
}
