package net.eugenpaul.adventofcode.y2015.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day2
 */
class Test2015Day2 {

    @Test
    void testTest2015Day2() {
        testPuzzle("2x3x4", 58, 34);
        testPuzzle("1x1x10", 43, 14);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, long totalSquare, long totalRibbon) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(List.of(Square.fromString(inputData))), "Testdata " + inputData);
        assertEquals(totalSquare, event.getTotalSquare());
        assertEquals(totalRibbon, event.getTotalRibbon());
    }

}