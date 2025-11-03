package net.eugenpaul.adventofcode.y2024.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testTest2024Day11() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day11/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day11 event = new Day11();
        assertEquals(55312, event.doPuzzle1(testFilePath));
        // assertEquals(0, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(194482L, event.getTotalScore());
        assertEquals(232454623677743L, event.getTotalScore2());
    }
}
