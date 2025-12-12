package net.eugenpaul.adventofcode.y2025.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2025Day12_1() {
        Day12 event = new Day12();
        assertEquals(2, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day12/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(497, event.getTotalScore());
        assertEquals(497, event.getTotalScore2());
    }
}
