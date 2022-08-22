package net.eugenpaul.adventofcode.y2021.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testTest2021Day11() {
        testPuzzle(List.of(//
                "5483143223", //
                "2745854711", //
                "5264556173", //
                "6141336146", //
                "6357385478", //
                "4167524645", //
                "2176841721", //
                "6882881134", //
                "4846848554", //
                "5283751526" //
        ), 1656, 195);
    }

    private void testPuzzle(List<String> inputData, long flashes, long firstSynchronizing) {
        Day11 event = new Day11();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(flashes, event.getFlashes());
        assertEquals(firstSynchronizing, event.getFirstSynchronizing());
    }

    @Test
    void testSolution2021Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1625, event.getFlashes());
        assertEquals(244, event.getFirstSynchronizing());
    }

}
