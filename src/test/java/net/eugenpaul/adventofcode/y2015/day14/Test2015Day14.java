package net.eugenpaul.adventofcode.y2015.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day14
 */
class Test2015Day14 {

    @Test
    void testTest2015Day14() {
        testPuzzle(List.of(//
                "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.", //
                "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds." //
        ), 1000, 1120, 689);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, int seconds, int bestDistance, int winnerPoints) {
        Day14 event = new Day14();
        event.setPuzzleTime(seconds);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(bestDistance, event.getMaxDistance());
        assertEquals(winnerPoints, event.getWinnerPoints());
    }

    @Test
    void testSolution2015Day14() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2696, event.getMaxDistance());
        assertEquals(1084, event.getWinnerPoints());
    }

}