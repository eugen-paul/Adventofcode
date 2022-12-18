package net.eugenpaul.adventofcode.y2022.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2022Day18() {
        testPuzzle("y2022/day18/test_puzzle1.txt", 64, 58);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day18 event = new Day18();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(4192, event.getPart1());
        assertEquals(2520, event.getPart2());
    }

}
