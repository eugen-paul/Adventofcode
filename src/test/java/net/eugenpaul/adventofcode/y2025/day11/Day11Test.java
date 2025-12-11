package net.eugenpaul.adventofcode.y2025.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testTest2025Day11_1() {
        Day11 event = new Day11();
        assertEquals(5, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day11/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day11_2() {
        Day11 event = new Day11();
        assertEquals(2, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day11/test_puzzle2.txt")));
    }

    @Test
    void testSolution2025Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(566, event.getTotalScore());
        assertEquals(331837854931968L, event.getTotalScore2());
    }
}
