package net.eugenpaul.adventofcode.y2024.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2024Day1() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day1/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day1 event = new Day1();
        assertEquals(11, event.doPuzzle1(testFilePath));
        assertEquals(31, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1530215, event.getTotalScore());
        assertEquals(26800609, event.getTotalScore2());
    }
}
