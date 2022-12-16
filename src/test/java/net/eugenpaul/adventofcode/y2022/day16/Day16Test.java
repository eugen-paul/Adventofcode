package net.eugenpaul.adventofcode.y2022.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2022Day16() {
        testPuzzle("y2022/day16/test_puzzle1.txt", 1651, 1707);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day16 event = new Day16();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1751, event.getPart1());
        assertEquals(2207, event.getPart2());
    }

}
