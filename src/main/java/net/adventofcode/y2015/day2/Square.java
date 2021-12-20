package net.adventofcode.y2015.day2;

public class Square {
    private int length;
    private int width;
    private int height;

    public Square(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

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

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
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
