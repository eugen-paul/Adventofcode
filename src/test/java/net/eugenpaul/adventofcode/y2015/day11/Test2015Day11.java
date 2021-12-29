package net.eugenpaul.adventofcode.y2015.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day11
 */
public class Test2015Day11 {

    @Test
    public void testTest2015Day11() {
        testPuzzle("abcdefgh", "abcdffaa");
        testPuzzle("ghijklmn", "ghjaabcc");

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, String nextPassword) {
        Day11 event = new Day11();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(nextPassword, event.getNextPassword());
    }

}