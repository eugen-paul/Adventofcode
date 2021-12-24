package net.adventofcode.y2015.day6.command;

import net.adventofcode.y2015.day6.ILightCommand;

public interface ILightCommandCreate {
    public ILightCommand create(int fromX, int fromY, int toX, int toY);
}
