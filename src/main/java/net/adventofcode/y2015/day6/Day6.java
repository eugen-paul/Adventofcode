package net.adventofcode.y2015.day6;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.adventofcode.helper.FileReaderHelper;
import net.adventofcode.y2015.day6.command.CommandFactory;
import net.adventofcode.y2015.day6.grid.BrightnesGrid;
import net.adventofcode.y2015.day6.grid.LightsGrid;

public class Day6 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day6.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day6.class.getName());

    private LightsGrid lightsGrid;
    private BrightnesGrid brightnesGrid;

    public int getLitLightsCount() {
        return lightsGrid.getLitLightsCount();
    }

    public int getBrightness() {
        return brightnesGrid.getBrightness();
    }

    public Day6(int sizeX, int sizeY) {
        lightsGrid = new LightsGrid(sizeX, sizeY);
        brightnesGrid = new BrightnesGrid(sizeX, sizeY);
    }

    public static void main(String[] args) {
        Day6 puzzle = new Day6(1000, 1000);
        puzzle.doPuzzleFromFile("y2015/day6/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "lights are lit: " + lightsGrid.getLitLightsCount());
        logger.log(Level.INFO, () -> "total brightness: " + brightnesGrid.getBrightness());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        for (String commando : eventData) {
            try {
                ILightCommand command = CommandFactory.getCommand(commando);
                command.doCommand(lightsGrid);
                command.doCommand(brightnesGrid);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error!", e);
                return false;
            }
        }

        return true;
    }

}
