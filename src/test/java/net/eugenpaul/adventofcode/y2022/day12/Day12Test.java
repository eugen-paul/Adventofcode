package net.eugenpaul.adventofcode.y2022.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2022Day12() {
        testPuzzle("y2022/day12/test_puzzle1.txt", 31, 29);
    }

    private void testPuzzle(String inputFile, long stepsPart1, long stepsPart2) {
        Day12 event = new Day12();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(stepsPart1, event.getStepsPart1());
        assertEquals(stepsPart2, event.getStepsPart2());
    }

    @Test
    void testSolution2022Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(437, event.getStepsPart1());
        assertEquals(430, event.getStepsPart2());
    }

}
