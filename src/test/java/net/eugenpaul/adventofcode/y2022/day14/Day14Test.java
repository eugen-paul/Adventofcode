package net.eugenpaul.adventofcode.y2022.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day14Test {

    @Test
    void testTest2022Day14() {
        testPuzzle("y2022/day14/test_puzzle1.txt", 24, 93);
    }

    private void testPuzzle(String inputFile, long stepsPart1, long stepsPart2) {
        Day14 event = new Day14();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(stepsPart1, event.getUnitsOfSand());
        assertEquals(stepsPart2, event.getUnitsOfSand2());
    }

    @Test
    void testSolution2022Day14() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1072, event.getUnitsOfSand());
        assertEquals(24659, event.getUnitsOfSand2());
    }

}
