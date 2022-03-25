package net.eugenpaul.adventofcode.y2017.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day14Test {

    @Test
    void testTest2017Day14_puzzle1() {
        testPuzzle1(List.of(//
                "flqrgnkx" //
        ), 8108, 1242);
    }

    private void testPuzzle1(List<String> inputData, int squares, int regions) {
        Day14 event = new Day14();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(squares, event.getSquares());
        assertEquals(regions, event.getRegions());
    }
}
