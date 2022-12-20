package net.eugenpaul.adventofcode.y2022.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day20Test {

    @Test
    void testTest2022Day20() {
        testPuzzle("y2022/day20/test_puzzle1.txt", 3, 1623178306);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day20 event = new Day20();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day20() {
        Day20 event = new Day20();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day20/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(7713, event.getPart1());
        assertEquals(1664569352803L, event.getPart2());
    }

}
