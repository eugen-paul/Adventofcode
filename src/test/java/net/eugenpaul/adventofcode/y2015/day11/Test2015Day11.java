package net.eugenpaul.adventofcode.y2015.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day11
 */
class Test2015Day11 {

    @Test
    void testTest2015Day11() {
        testPuzzle("abcdefgh", "abcdffaa");
        testPuzzle("ghijklmn", "ghjaabcc");

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, String nextPassword) {
        Day11 event = new Day11();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(nextPassword, event.getNextPassword());
    }

    @Test
    void testSolution2015Day11() {
        Day11 event = new Day11();

        String eventData = FileReaderHelper.readStringFromFile("y2015/day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("cqjxxyzz", event.getNextPassword());
        assertEquals("cqkaabcc", event.getNextPassword2());
    }

}