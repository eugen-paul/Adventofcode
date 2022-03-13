package net.eugenpaul.adventofcode.helper;

public class MathHelper {
    private MathHelper() {

    }

    public static long sum(Long from, long to) {
        return (to * to + to - (from - 1L) * (from - 1L) - (from - 1L)) / 2L;
    }
}
