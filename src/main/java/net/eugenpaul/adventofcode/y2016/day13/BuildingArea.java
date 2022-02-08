package net.eugenpaul.adventofcode.y2016.day13;

public class BuildingArea {

    private int favoriteNumber;

    public BuildingArea(int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public boolean isOpenArea(int x, int y) {
        int magic = x * x + 3 * x + 2 * x * y + y + y * y;
        magic += favoriteNumber;

        long bit = 1;
        int bitCount = 0;
        do {
            if ((bit & magic) != 0) {
                bitCount++;
            }
            bit = bit << 1;
        } while (bit != 0x8000_0000L);

        return bitCount % 2 == 0;
    }
}
