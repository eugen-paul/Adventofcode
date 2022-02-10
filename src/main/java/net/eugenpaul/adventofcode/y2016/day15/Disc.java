package net.eugenpaul.adventofcode.y2016.day15;

import lombok.Data;

@Data
public class Disc {

    private int number;
    private int positionCount;
    private int startPosition;

    public Disc(int number, int positionCount, int startPosition) {
        this.number = number;
        this.positionCount = positionCount;
        this.startPosition = startPosition;
    }

    public static Disc fromString(String data) {
        String[] elements = data.split(" ");
        int number = Integer.parseInt(elements[1].substring(1));
        int positionCount = Integer.parseInt(elements[3]);
        int startPosition = Integer.parseInt(elements[11].substring(0, elements[11].length() - 1));

        return new Disc(number, positionCount, startPosition);
    }

    public int getPositionAtTime(int time) {
        return (startPosition + time) % positionCount;
    }
}
