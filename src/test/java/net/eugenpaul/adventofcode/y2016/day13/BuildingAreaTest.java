package net.eugenpaul.adventofcode.y2016.day13;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class BuildingAreaTest {

    @Test
    void testIsOpenArea() {
        BuildingArea area = new BuildingArea(10);

        String testArea = ".#.####.##..#..#...##....##...###.#.###..##..#..#...##....#.#...##.###";

        for (int i = 0; i < testArea.length(); i++) {
            if (testArea.charAt(i) == '.') {
                assertTrue(area.isOpenArea(i % 10, i / 10));
            } else {
                assertFalse(area.isOpenArea(i % 10, i / 10));
            }
        }
    }
}
