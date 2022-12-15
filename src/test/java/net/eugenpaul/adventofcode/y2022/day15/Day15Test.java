package net.eugenpaul.adventofcode.y2022.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day15Test {

    @Test
    void testTest2022Day15() {
        testPuzzle("y2022/day15/test_puzzle1.txt", 26, 56000011);
    }

    private void testPuzzle(String inputFile, long stepsPart1, long stepsPart2) {
        Day15 event = new Day15();

        event.setPart1Line(10);
        event.setPart2Size(20);
        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(stepsPart1, event.getUnitsOfSand());
        assertEquals(stepsPart2, event.getUnitsOfSand2());
    }

    @Test
    void testSolution2022Day15() {
        Day15 event = new Day15();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day15/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(5403290, event.getUnitsOfSand());
        assertEquals(10291582906626L, event.getUnitsOfSand2());
    }

}
