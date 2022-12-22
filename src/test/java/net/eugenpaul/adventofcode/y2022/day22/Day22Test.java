package net.eugenpaul.adventofcode.y2022.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day22Test {

    @Test
    void testTest2022Day22() {
        testPuzzle("y2022/day22/test_puzzle1.txt", 6032, 5031);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day22 event = new Day22();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        // assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day22() {
        Day22 event = new Day22();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day22/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(149250, event.getPart1());
        assertEquals(12462, event.getPart2());
    }

}
