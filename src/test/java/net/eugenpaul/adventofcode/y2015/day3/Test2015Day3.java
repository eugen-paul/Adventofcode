package net.eugenpaul.adventofcode.y2015.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day2
 */
class Test2015Day3 {

    @Test
    void testTest2015Day3() {
        testPuzzle1(">", 2);
        testPuzzle1("^>v<", 4);
        testPuzzle1("^v^v^v^v^v", 2);

        testPuzzle2("^v", 3);
        testPuzzle2("^>v<", 3);
        testPuzzle2("^v^v^v^v^v", 11);

        System.out.println("All tests OK.");
    }

    private void testPuzzle1(String inputData, long visitedHouseCount) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData), "Testdata " + inputData);
        assertEquals(visitedHouseCount, event.getVisitedHouseCountSantaAlone().longValue());
    }

    private void testPuzzle2(String inputData, long visitedHouseCount) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData), "Testdata " + inputData);
        assertEquals(visitedHouseCount, event.getVisitedHouseCountSantaAndRobo().longValue());
    }

    @Test
    void testSolution2015Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2572, event.getVisitedHouseCountSantaAlone());
        assertEquals(2631, event.getVisitedHouseCountSantaAndRobo());
    }

}