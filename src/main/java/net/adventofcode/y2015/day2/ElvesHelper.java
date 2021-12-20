package net.adventofcode.y2015.day2;

public class ElvesHelper {
    private ElvesHelper() {
    }

    public static long computeSurfaceArea(Square box) {
        return 2 * box.getLength() * box.getWidth()//
                + 2 * box.getWidth() * box.getHeight()//
                + 2 * box.getHeight() * box.getLength()//
                + box.getSmallestSide();
    }

    public static long computeRibbon(Square box) {
        return computeShortestDistance(box) + box.getLength() * box.getHeight() * box.getWidth();
    }

    private static long computeShortestDistance(Square box) {
        if (box.getLength() >= box.getHeight() && box.getLength() >= box.getWidth()) {
            return (long) box.getHeight() * 2 + box.getWidth() * 2;
        }

        if (box.getHeight() >= box.getLength() && box.getHeight() >= box.getWidth()) {
            return (long) box.getLength() * 2 + box.getWidth() * 2;
        }

        return (long) box.getLength() * 2 + box.getHeight() * 2;
    }
}
