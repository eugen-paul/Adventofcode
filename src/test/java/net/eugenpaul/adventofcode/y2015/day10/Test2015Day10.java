package net.eugenpaul.adventofcode.y2015.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day10
 */
class Test2015Day10 {

    @Test
    void testTest2015Day10() {
        testPuzzle("1", 1, "11");
        testPuzzle("1", 2, "21");
        testPuzzle("1", 3, "1211");
        testPuzzle("1", 4, "111221");
        testPuzzle("1", 5, "312211");

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, int steps, String result) {
        Day10 event = new Day10();

        event.setSteps(steps);
        event.setAdditionalSteps(0);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(result.length(), event.getLengthPuzzle1());
    }

    @Test
    void testSolution2015Day10() {
        Day10 event = new Day10();

        String eventData = FileReaderHelper.readStringFromFile("y2015/day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(492982, event.getLengthPuzzle1());
        assertEquals(6989950, event.getLengthPuzzle2());
    }

}