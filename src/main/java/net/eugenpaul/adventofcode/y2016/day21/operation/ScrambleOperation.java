package net.eugenpaul.adventofcode.y2016.day21.operation;

public interface ScrambleOperation {
    public StringBuilder execute(StringBuilder input);
    public StringBuilder reverse(StringBuilder input);
}
