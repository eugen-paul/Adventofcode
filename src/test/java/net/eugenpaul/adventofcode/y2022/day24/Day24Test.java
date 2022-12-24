package net.eugenpaul.adventofcode.y2022.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day24Test {

    @Test
    void testTest2022Day24() {
        testPuzzle("y2022/day24/test_puzzle1.txt", 18, 54);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day24 event = new Day24();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(322, event.getPart1());
        assertEquals(974, event.getPart2());
    }

}
