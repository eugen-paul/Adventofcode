package net.eugenpaul.adventofcode.y2016.day1;

public enum Turn {
    R, L;

    public static Turn fromString(String data) {
        return (data.startsWith("R")) ? R : L;
    }
}
