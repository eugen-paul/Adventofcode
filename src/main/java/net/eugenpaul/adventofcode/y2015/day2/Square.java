package net.eugenpaul.adventofcode.y2015.day2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Square {
    private int length;
    private int width;
    private int height;

    public static Square fromString(String data) {
        String[] params = data.split("x");
        if (3 != params.length) {
            return null;
        }
        try {
            return new Square(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
        } catch (Exception e) {
            return null;
        }
    }

    public long getSmallestSide() {
        long sideLW = (long) length * width;
        long sideWH = (long) width * height;
        long sideHL = (long) height * length;
        if (sideLW <= sideWH && sideLW <= sideHL) {
            return sideLW;
        }
        if (sideWH <= sideLW && sideWH <= sideHL) {
            return sideWH;
        }
        return sideHL;
    }

}
