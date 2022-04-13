package net.eugenpaul.adventofcode.y2015.day6;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.y2015.day6.command.CommandFactory;
import net.eugenpaul.adventofcode.y2015.day6.grid.BrightnesGrid;
import net.eugenpaul.adventofcode.y2015.day6.grid.LightsGrid;

public class Day6 extends SolutionTemplate {

    @Getter
    private LightsGrid lightsGrid;
    @Getter
    private BrightnesGrid brightnesGrid;

    public Day6(int sizeX, int sizeY) {
        lightsGrid = new LightsGrid(sizeX, sizeY);
        brightnesGrid = new BrightnesGrid(sizeX, sizeY);
    }

    public static void main(String[] args) {
        Day6 puzzle = new Day6(1000, 1000);
        puzzle.doPuzzleFromFile("y2015/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
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

        logger.log(Level.INFO, () -> "lights are lit: " + lightsGrid.getLitLightsCount());
        logger.log(Level.INFO, () -> "total brightness: " + brightnesGrid.getBrightness());
        return true;
    }

}
