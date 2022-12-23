package net.eugenpaul.adventofcode.y2022.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testTest2022Day23() {
        testPuzzle("y2022/day23/test_puzzle1.txt", 110, 20);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day23 event = new Day23();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3800, event.getPart1());
        assertEquals(916, event.getPart2());
    }

}
