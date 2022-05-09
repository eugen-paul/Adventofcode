package net.eugenpaul.adventofcode.y2018.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testTest2018Day17_testPuzzle1() {
        testPuzzle1(List.of(//
                "x=495, y=2..7", //
                "y=7, x=495..501", //
                "x=501, y=3..7", //
                "x=498, y=2..4", //
                "x=506, y=1..2", //
                "x=498, y=10..13", //
                "x=504, y=10..13", //
                "y=13, x=498..504" //
        ), 57, 29);

    }

    private void testPuzzle1(List<String> inputData, int tilesOfWater, int tilesOfWaterLeft) {
        Day17 event = new Day17();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(tilesOfWater, event.getTilesOfWater());
        assertEquals(tilesOfWaterLeft, event.getTilesOfWaterLeft());
    }

    @Test
    void testSolution2018Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(33242, event.getTilesOfWater());
        assertEquals(27256, event.getTilesOfWaterLeft());
    }

}
