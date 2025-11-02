package net.eugenpaul.adventofcode.y2024.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2024Day5() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day5/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day5 event = new Day5();
        assertEquals(143, event.doPuzzle1(testFilePath));
        assertEquals(123, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(4689, event.getTotalScore());
        assertEquals(6336, event.getTotalScore2());
    }
}
