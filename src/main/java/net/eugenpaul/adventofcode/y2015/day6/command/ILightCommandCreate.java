package net.eugenpaul.adventofcode.y2015.day6.command;

import net.eugenpaul.adventofcode.y2015.day6.ILightCommand;

public interface ILightCommandCreate {
    public ILightCommand create(int fromX, int fromY, int toX, int toY);
}
