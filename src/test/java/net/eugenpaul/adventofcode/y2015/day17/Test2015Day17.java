package net.eugenpaul.adventofcode.y2015.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

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
                4, //
                3//
        );

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, int combinations, int minCombinations) {
        Day17 event = new Day17();
        event.setLitersOfEggnog(25);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(combinations, event.getCombinations());
        assertEquals(minCombinations, event.getMinContainerCombinations());
    }

    @Test
    void testSolution2015Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1638, event.getCombinations());
        assertEquals(17, event.getMinContainerCombinations());
    }

}