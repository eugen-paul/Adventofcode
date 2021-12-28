package net.eugenpaul.adventofcode.y2015.day6;

public interface IGrid {

    public void turnOn(int fromX, int fromY, int toX, int toY);

    public void turnOff(int fromX, int fromY, int toX, int toY);

    public void toggle(int fromX, int fromY, int toX, int toY);
}
