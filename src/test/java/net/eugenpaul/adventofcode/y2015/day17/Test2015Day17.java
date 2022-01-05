package net.eugenpaul.adventofcode.y2015.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day17
 */
class Test2015Day17 {

    @Test
    void testTest2015Day17() {
        testPuzzle(//
                List.of("20", //
                        "15", //
                        "10", //
                        "5", //
                        "5"//
                ), //
                "25", //
                4,//
                3//
        );

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, String liters, int combinations, int minCombinations) {
        Day17 event = new Day17();

        assertTrue(event.doPuzzleFromData(inputData, liters));
        assertEquals(combinations, event.getCombinations());
        assertEquals(minCombinations, event.getMinContainerCombinations());
    }

}