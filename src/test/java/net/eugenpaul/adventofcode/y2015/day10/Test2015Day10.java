package net.eugenpaul.adventofcode.y2015.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day10
 */
public class Test2015Day10 {

    @Test
    public void testTest2015Day10() {
        testPuzzle("1", 1, "11");
        testPuzzle("1", 2, "21");
        testPuzzle("1", 3, "1211");
        testPuzzle("1", 4, "111221");
        testPuzzle("1", 5, "312211");

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, int steps, String result) {
        Day10 event = new Day10();

        assertTrue(event.doPuzzleFromData(inputData, steps));
        assertEquals(result, event.getResult());
    }

}