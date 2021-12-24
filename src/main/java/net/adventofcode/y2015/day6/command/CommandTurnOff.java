package net.adventofcode.y2015.day6.command;

import net.adventofcode.y2015.day6.IGrid;
import net.adventofcode.y2015.day6.ILightCommand;

public class CommandTurnOff implements ILightCommand {

    public static final String START_WITH = "turn off";

    int fromX;
    int fromY;
    int toX;
    int toY;

    public CommandTurnOff(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public void doCommand(IGrid grid) {
        grid.turnOff(fromX, fromY, toX, toY);
    }
}
