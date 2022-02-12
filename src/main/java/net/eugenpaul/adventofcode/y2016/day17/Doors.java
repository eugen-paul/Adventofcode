package net.eugenpaul.adventofcode.y2016.day17;

import lombok.Getter;

@Getter
public enum Doors {
    UP("U", 0, -1), //
    DOWN("D", 0, 1), //
    LEFT("L", -1, 0), //
    RIGHT("R", 1, 0), //
    ;

    private String value;
    private int xChange;
    private int yChange;

    private Doors(String value, int xChange, int yChange) {
        this.value = value;
        this.xChange = xChange;
        this.yChange = yChange;
    }
}
