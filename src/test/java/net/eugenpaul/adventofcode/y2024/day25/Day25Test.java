package net.eugenpaul.adventofcode.y2024.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day25Test {

    @Test
    void testTest2024Day25_1() {
        Day25 event = new Day25();
        assertEquals(3, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day25/test_puzzle.txt")));
    }

    @Test
    void testSolution2024Day25() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day25/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(3291, event.getTotalScore());
    }
}
