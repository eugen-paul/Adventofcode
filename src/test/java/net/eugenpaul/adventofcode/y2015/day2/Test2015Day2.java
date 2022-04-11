package net.eugenpaul.adventofcode.y2015.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

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

        assertTrue(event.doEvent(List.of(inputData)), "Testdata " + inputData);
        assertEquals(totalSquare, event.getTotalSquare());
        assertEquals(totalRibbon, event.getTotalRibbon());
    }

    @Test
    void testSolution2015Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1598415, event.getTotalSquare());
        assertEquals(3812909, event.getTotalRibbon());
    }


}