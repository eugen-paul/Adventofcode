package net.eugenpaul.adventofcode.y2015.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day20
 */
class Test2015Day20 {

    @Test
    void testTest2015Day20() {
        testPuzzle1("10", 1);
        testPuzzle1("30", 2);
        testPuzzle1("40", 3);
        testPuzzle1("70", 4);
        testPuzzle1("60", 4);
        testPuzzle1("120", 6);
        testPuzzle1("80", 6);
        testPuzzle1("150", 8);
        testPuzzle1("130", 8);

        System.out.println("All tests OK.");
    }

    private void testPuzzle1(String inputData, int lowestHouseNumber) {
        Day20 event = new Day20();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(lowestHouseNumber, event.getLowestHouseNumber());
    }

    @Test
    void testSolution2015Day20() {
        Day20 event = new Day20();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day20/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(665280, event.getLowestHouseNumber());
        assertEquals(705600, event.getLowestHouseNumberPart2());
    }

}