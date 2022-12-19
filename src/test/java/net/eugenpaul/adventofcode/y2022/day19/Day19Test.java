package net.eugenpaul.adventofcode.y2022.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day19Test {

    @Test
    void testTest2022Day19() {
        testPuzzle("y2022/day19/test_puzzle1.txt", 33, 3472);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day19 event = new Day19();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day19() {
        Day19 event = new Day19();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day19/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2301, event.getPart1());
        assertEquals(10336, event.getPart2());
    }

}
