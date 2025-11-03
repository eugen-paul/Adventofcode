package net.eugenpaul.adventofcode.y2024.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2024Day9() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day9/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day9 event = new Day9();
        assertEquals(1928, event.doPuzzle1(testFilePath));
        assertEquals(2858, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(6430446922192L, event.getTotalScore());
        assertEquals(6460170593016L, event.getTotalScore2());
    }
}
