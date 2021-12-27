package net.adventofcode.y2015.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day9
 */
public class Test2015Day9 {

    @Test
    public void test() {
        testPuzzle(List.of(//
                "London to Dublin = 464", //
                "London to Belfast = 518", //
                "Dublin to Belfast = 141"//
        ), 605, 982);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, int shortestRouteLength, int longestRouteLength) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(shortestRouteLength, event.getShortestRoteLength());
        assertEquals(longestRouteLength, event.getLongestRoteLength());
    }

}