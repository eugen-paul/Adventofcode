package net.eugenpaul.adventofcode.y2022.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testTest2022Day17() {
        testPuzzle("y2022/day17/test_puzzle1.txt", 3068, 1514285714288L);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day17 event = new Day17();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3135, event.getPart1());
        assertEquals(1569054441243L, event.getPart2());
    }

}
