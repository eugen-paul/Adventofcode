package net.eugenpaul.adventofcode.y2015.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day6
 */
public class Test2015Day6 {

    @Test
    public void testTest2015Day6() {
        testPuzzle(List.of("turn on 0,0 through 9,9"), 100, 100);
        testPuzzle(List.of("turn on 0,0 through 9,1"), 20, 20);
        testPuzzle(List.of("turn on 0,1 through 9,1"), 10, 10);
        testPuzzle(List.of("toggle 0,0 through 9,0"), 10, 20);
        testPuzzle(List.of("toggle 0,1 through 9,1"), 10, 20);
        testPuzzle(List.of("toggle 0,0 through 9,1"), 20, 40);
        testPuzzle(List.of("toggle 0,0 through 9,0", "toggle 0,0 through 9,0"), 0, 40);
        testPuzzle(List.of("turn off 0,0 through 9,9"), 0, 0);
        testPuzzle(List.of("turn on 0,0 through 9,9", "turn off 0,0 through 9,9"), 0, 0);
        testPuzzle(List.of("turn on 0,0 through 9,9", "turn off 0,0 through 9,0"), 90, 90);
        testPuzzle(List.of("turn on 0,0 through 9,9", "turn off 0,0 through 9,1"), 80, 80);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, long lights, long brightness) {
        Day6 event = new Day6(10, 10);

        assertTrue(event.doPuzzleFromData(inputData), "Testdata " + inputData);
        assertEquals(lights, event.getLitLightsCount());
        assertEquals(brightness, event.getBrightness());
    }

}